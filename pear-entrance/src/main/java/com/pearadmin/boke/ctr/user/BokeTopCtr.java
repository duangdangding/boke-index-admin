package com.pearadmin.boke.ctr.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pearadmin.boke.service.BokeTopService;
import com.pearadmin.boke.entry.BokeTop;
import com.pearadmin.boke.utils.contains.BaseCtr;
import com.pearadmin.boke.vo.ResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BokeTopCtr extends BaseCtr {

    @Autowired
    private BokeTopService bokeTopService;

    @RequestMapping("/boke/top")
    public ResultDto<String> setTop(BokeTop top) {
        int count = bokeTopService.getByuserIdCount(top.getUserId());
        if (count >= 3) {
            return fail("最多只能置顶3个");
        }
        boolean save = bokeTopService.save(top);
        return returnDto(save);
    }

    @RequestMapping("/boke/delTop")
    public ResultDto<String> removeTop(BokeTop top) {
        QueryWrapper<BokeTop> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",top.getUserId());
        wrapper.eq("boke_id",top.getBokeId());
        boolean remove = bokeTopService.remove(wrapper);
        return returnDto(remove);
    }
}
