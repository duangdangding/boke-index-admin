package com.pearadmin.system.controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.pearadmin.common.constant.ControllerConstant;
import com.pearadmin.common.web.base.BaseController;
import com.pearadmin.common.web.domain.response.Result;
import com.pearadmin.common.web.domain.response.module.ResultTable;
import com.pearadmin.common.web.session.HttpSessionContext;
import com.pearadmin.common.web.session.HttpSessionContextHolder;
import com.pearadmin.system.domain.SysOnlineUser;
import com.pearadmin.system.domain.SysUser;

import io.swagger.annotations.Api;

/**
 * Describe: 在线用户控制器
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 */
@RestController
@Api(tags = {"在线用户"})
@RequestMapping(ControllerConstant.API_SYSTEM_PREFIX + "online")
public class SysOnlineController extends BaseController {

    @Resource
    private SessionRegistry sessionRegistry;

    /**
     * Describe: 在线用户列表
     * Param: username
     * Return: ModelAndView
     */
    @GetMapping("data")
    @PreAuthorize("hasPermission('/system/online/data','sys:online:data')")
    public ResultTable data(String username) {
        List<Object> allPrincipalsUser = sessionRegistry.getAllPrincipals();
        List<SysOnlineUser> onlineUser = new ArrayList<>();
        for (Object obj : allPrincipalsUser) {
            SysOnlineUser sysOnlineUser = new SysOnlineUser();
            SysUser objs = (SysUser) obj;
            sysOnlineUser.setUserId(objs.getUserId() + "");
            sysOnlineUser.setUsername(objs.getUsername());
            sysOnlineUser.setRealName(objs.getRealName());
            sysOnlineUser.setLastTime(objs.getLastTime());
            sysOnlineUser.setOnlineTime(Duration.between(objs.getLastTime(), LocalDateTime.now()).toMinutes() + "分钟");
            onlineUser.add(sysOnlineUser);
        }
        // TODO 使用 Stream 进行内存数据过滤
        return dataTable(onlineUser);
    }

    /**
     * Describe: 获取在线用户列表视图
     * Return: ModelAndView
     */
    @GetMapping("main")
    @PreAuthorize("hasPermission('/system/online/main','sys:online:main')")
    public ModelAndView main() {
        return jumpPage("system/user/online");
    }


    /**
     * Describe: 踢出用户（下线）
     * Param: onlineId
     * Return: ModelAndView
     */
    @DeleteMapping("/remove/{onlineId}")
    @ResponseBody
    public Result remove(@PathVariable String onlineId) {
        // 从sessionRegistry中获取所有的用户信息
        List<Object> principals = sessionRegistry.getAllPrincipals();
        for (Object principal : principals) {
            SysUser userDetails = (SysUser) principal;
            String userId = userDetails.getUserId() + "";
            if (onlineId.equals(userId)) {
                // 不允许操作admin用户下线
                if ("admin".equals(userDetails.getUsername())) {
                    return failure("不允许操作超级管理员[admin]下线");
                }
                for (SessionInformation sessionInformation : sessionRegistry.getAllSessions(userDetails, false)) {
                    sessionInformation.expireNow();
                    // 从sessionRegistry中清除session信息
                    sessionRegistry.removeSessionInformation(sessionInformation.getSessionId());
                    HttpSessionContext sessionContext = HttpSessionContextHolder.currentSessionContext();
                    // 销毁session
                    sessionContext.getSession(sessionInformation.getSessionId()).invalidate();
                }
                return success(String.format("用户[%s]已下线", userDetails.getRealName()));
            }
        }
        return failure();
    }
}
