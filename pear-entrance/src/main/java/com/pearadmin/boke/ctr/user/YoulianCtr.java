package com.pearadmin.boke.ctr.user;

import com.pearadmin.boke.service.YoulianService;
import com.pearadmin.boke.entry.FriendUrl;
import com.pearadmin.boke.utils.contains.BaseCtr;
import com.pearadmin.boke.vo.ResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author ycf
 * @Description $
 * @Date 15:20
 * @Version 1.0
 */
@RestController
public class YoulianCtr extends BaseCtr {
    @Autowired
    private YoulianService youlianService;

    @RequestMapping("/showyoulian")
    public ResultDto<List<FriendUrl>> getShow(){
        List<FriendUrl> list = youlianService.getShow();
        return success(list);
    }
}
