package com.pearadmin.boke.ctr.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.pearadmin.boke.entry.SongList;
import com.pearadmin.boke.service.SongListService;
import com.pearadmin.boke.utils.RedisUtil;
import com.pearadmin.boke.utils.contains.BaseCtr;
import com.pearadmin.boke.utils.contains.Constants;
import com.pearadmin.boke.vo.ResultDto;

import lombok.extern.slf4j.Slf4j;

/**
* SongList控制层
*
* @author lushao
* @version 1.0.0 2021-06-16 09:43:56
*/
@Slf4j
@RestController
public class SongListCtr extends BaseCtr {
    
    @Autowired
    private SongListService songListService;
    @Autowired
    private RedisUtil redisUtil;
    
    @GetMapping("/song/{userId}")
    public ResultDto<SongList> getByUserId(@PathVariable("userId") Long userId) {
        SongList byUserId = songListService.getByUserId(userId);
        return success(byUserId);
    }
    
    @GetMapping("/song/updateById")
    @PreAuthorize("hasPermission('/song/updateById','sys:song:updateById')")
    public ResultDto<SongList> updateById(SongList song) {
        boolean b = songListService.updateById(song);
        if (b) {
            String key = Constants.RedisKey.SONGLIST + "0";
            redisUtil.del(key);
        }
        return success(b);
    }
    
}