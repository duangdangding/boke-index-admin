package com.pearadmin.boke.ctr.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pearadmin.boke.service.BokeTopService;
import com.pearadmin.boke.entry.BokeTop;
import com.pearadmin.boke.utils.TokenUtil;
import com.pearadmin.boke.utils.contains.BaseCtr;
import com.pearadmin.boke.utils.contains.Constants;
import com.pearadmin.boke.vo.ResultDto;
import com.pearadmin.boke.utils.contains.UserLoginToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TopOrderCtr extends BaseCtr {
    
    @Autowired
    private BokeTopService bokeTopService;
    
    @UserLoginToken
    @RequestMapping("/t/top/add")
    public ResultDto<String> setTop(Integer bokeId) {
        if (TokenUtil.USERID == null) {
            return fail(LOGIN);
        }
        List<BokeTop> byuserId = bokeTopService.getByuserId(TokenUtil.USERID);
        if (byuserId.isEmpty() || byuserId.size() < Constants.BokeXZ.ZD) {
            BokeTop top = new BokeTop();
            top.setUserId(TokenUtil.USERID);
            top.setBokeId(bokeId);
            top.setTopOrder(1);
            boolean save = bokeTopService.save(top);
            return returnDto(save);
        } else {
            return fail(ZDERR);
        }
    }
    
    @UserLoginToken
    @RequestMapping("/t/top/cancel")
    public ResultDto<String> scancelTop(Integer bokeId) {
        if (TokenUtil.USERID == null) {
            return fail(LOGIN);
        }
        if (bokeId == null) {
            return fail(NODATA);
        }
        QueryWrapper<BokeTop> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",TokenUtil.USERID);
        wrapper.eq("boke_id",bokeId);
        boolean remove = bokeTopService.remove(wrapper);
        return returnDto(remove);
    }
}
