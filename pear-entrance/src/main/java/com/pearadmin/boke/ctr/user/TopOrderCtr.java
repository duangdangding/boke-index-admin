package com.pearadmin.boke.ctr.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pearadmin.boke.entry.BokeTop;
import com.pearadmin.boke.service.BokeTopService;
import com.pearadmin.boke.utils.contains.BaseCtr;
import com.pearadmin.boke.utils.contains.Constants;
import com.pearadmin.boke.vo.ResultDto;
import com.pearadmin.common.tools.SecurityUtil;
import com.pearadmin.system.domain.SysUser;

@RestController
public class TopOrderCtr extends BaseCtr {
    
    @Autowired
    private BokeTopService bokeTopService;
    
    @RequestMapping("/t/top/add")
    public ResultDto<String> setTop(Integer bokeId) {
        SysUser sysUser = SecurityUtil.currentUser();
        if (sysUser == null) {
            return fail(LOGIN);
        }
        Long userId = sysUser.getUserId();
        List<BokeTop> byuserId = bokeTopService.getByuserId(userId);
        if (byuserId.isEmpty() || byuserId.size() < Constants.BokeXZ.ZD) {
            BokeTop top = new BokeTop();
            top.setUserId(userId);
            top.setBokeId(bokeId);
            top.setTopOrder(1);
            boolean save = bokeTopService.save(top);
            return returnDto(save);
        } else {
            return fail(ZDERR);
        }
    }
    
    @RequestMapping("/t/top/cancel")
    public ResultDto<String> scancelTop(Integer bokeId) {
        SysUser sysUser = SecurityUtil.currentUser();
        if (sysUser == null) {
            return fail(LOGIN);
        }
        Long userId = sysUser.getUserId();
        if (bokeId == null) {
            return fail(NODATA);
        }
        QueryWrapper<BokeTop> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        wrapper.eq("boke_id",bokeId);
        boolean remove = bokeTopService.remove(wrapper);
        return returnDto(remove);
    }
}
