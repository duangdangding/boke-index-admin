package com.pearadmin.boke.ctr.user;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pearadmin.boke.entry.MessageWall;
import com.pearadmin.boke.service.MessageWallService;
import com.pearadmin.boke.utils.RedisUtil;
import com.pearadmin.boke.utils.contains.BaseCtr;
import com.pearadmin.boke.utils.contains.Constants;
import com.pearadmin.boke.vo.ResultDto;

@RestController
@RequestMapping("/u/wall/")
public class MessageWallCtr extends BaseCtr {
    
    @Autowired
    private MessageWallService messageWallService;
    
    @Autowired
    private RedisUtil redisUtil;
    
    @RequestMapping("add")
    public ResultDto addMessageWall(MessageWall messageWall) {
        boolean save = messageWallService.save(messageWall);
        messageWall.setCreateTime(new Date());
        if (save) {
            return success(messageWall);
        } else {
            return fail();
        }
    }
    
    @RequestMapping("list")
    public ResultDto eallList() {
        QueryWrapper<MessageWall> wrapper = new QueryWrapper<>();
        wrapper.eq("delete_state",0);
        List<MessageWall> list = messageWallService.list(wrapper);
        return success(list);
    }
    
    @RequestMapping("randomName")
    public ResultDto randomName() {
        Object o = redisUtil.sGetRan(Constants.RedisKey.randomName);
        return success(o);
    }
}
