package com.pearadmin.boke.ctr.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pearadmin.boke.service.DaoHangLanService;
import com.pearadmin.boke.utils.RedisUtil;
import com.pearadmin.boke.utils.contains.BaseCtr;
import com.pearadmin.boke.utils.contains.Constants;
import com.pearadmin.boke.vo.NavAndIcon;
import com.pearadmin.boke.vo.ResultDto;
import com.pearadmin.common.tools.SecurityUtil;
import com.pearadmin.system.domain.SysUser;

/**
 * @Author ycf
 * @Description $
 * @Date 17:27
 * @Version 1.0
 */
@RestController
@RequestMapping("")
public class DaoHangLanCtr extends BaseCtr {
    
    @Autowired
    private DaoHangLanService daoHangLanService;
    
    @Autowired
    private RedisUtil redisUtil;
    
    @RequestMapping("/indexNav")
    public ResultDto<NavAndIcon> indexNav() {
        SysUser sysUser = SecurityUtil.currentUser();
        NavAndIcon navAndIcon = daoHangLanService.indexNav(sysUser);
        return success(navAndIcon);
    }

    /**
     * 获取副标题
     * @return
     */
    @GetMapping("/title/sub")
    public ResultDto getSubtitle() {
        String title = null;
        try {
            if (redisUtil.hasKey(Constants.RedisKey.SUBTITLE)) {
                title = redisUtil.get(Constants.RedisKey.SUBTITLE).toString();
            }
        } catch (Exception e) {
            title = "";
        }
        return success(title);       
    }
}
