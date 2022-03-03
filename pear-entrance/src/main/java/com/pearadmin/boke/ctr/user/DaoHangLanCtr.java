package com.pearadmin.boke.ctr.user;

import com.pearadmin.boke.service.DaoHangLanService;
import com.pearadmin.boke.entry.Navigation;
import com.pearadmin.boke.utils.RedisUtil;
import com.pearadmin.boke.utils.contains.BaseCtr;
import com.pearadmin.boke.utils.contains.Constants;
import com.pearadmin.boke.vo.NavAndIcon;
import com.pearadmin.boke.vo.NavDto;
import com.pearadmin.boke.vo.ResultDto;
import com.pearadmin.boke.vo.ResultDtoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author ycf
 * @Description $
 * @Date 17:27
 * @Version 1.0
 */
@RestController
public class DaoHangLanCtr extends BaseCtr {
    
    @Autowired
    private DaoHangLanService daoHangLanService;
    
    @Autowired
    private RedisUtil redisUtil;
    
    @RequestMapping("/showdaohanglan")
    public ResultDto<List<Navigation>> getShowdaohanglan(){
        List<Navigation> list = daoHangLanService.list();
        if(list!=null&&list.size()>0){
            return ResultDtoManager.success(list);
        }
        return returnDto(0);
    }

    @GetMapping("/nav/all")
    public List<NavDto> getNavList() {
        return daoHangLanService.getNavList();
    }

    /**
     * 获取用户的标题栏
     * @param userId
     * @return
     */
    @GetMapping("/navicon/all/{userId}")
    public NavAndIcon getInfo(@PathVariable("userId") Integer userId) {
        return daoHangLanService.getInfo(userId);
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
            if (redisUtil.hasKey(Constants.RedisKey.SUBTITLE)) {
                title = redisUtil.get(Constants.RedisKey.SUBTITLE).toString();
            }
        } catch (Exception e) {
            title = "";
        }
        return success(title);       
    }
}
