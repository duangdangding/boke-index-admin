package com.pearadmin.boke.ctr.admin.birthday;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lsh.birthday.UserMsg;
import com.pearadmin.birthday.service.UserMsgService;
import com.pearadmin.boke.utils.contains.BaseCtr;
import com.pearadmin.boke.vo.query.PageVo;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("admin/birthday/usemsg")
public class UseMsgCtr extends BaseCtr {
    
    @Autowired
    private UserMsgService userMsgService;
    
    /**
     * Describe: 获取生日入场人员列表视图
     * Param ModelAndView
     * Return 生日入场人员列表视图
     */
    @GetMapping("main")
    @ApiOperation(value = "获取生日入场人员列表视图")
    @PreAuthorize("hasPermission('/admin/birthday/usemsg/main','sys:usemsg:main')")
    public ModelAndView main() {
        return getView(ADMINPATHPRE + "birthday/user");
    }
    
    @RequestMapping("/page")
    @PreAuthorize("hasPermission('/admin/birthday/page','sys:usemsg:page')")
    public Map<String,Object> getPage(PageVo vo) {
        IPage<UserMsg> page = userMsgService.getPage(vo);
        return convertLayuiPage(page);
    }
    
}
