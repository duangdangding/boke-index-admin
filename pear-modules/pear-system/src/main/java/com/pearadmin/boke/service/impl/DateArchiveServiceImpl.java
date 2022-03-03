package com.pearadmin.boke.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pearadmin.boke.entry.DateArchive;
import com.pearadmin.boke.service.DateArchiveService;
import com.pearadmin.boke.utils.RedisUtil;
import com.pearadmin.boke.utils.contains.Constants;
import com.lsh.mapper.boke.DateArchiveMapper;

import cn.hutool.json.JSONUtil;

@Service
public class DateArchiveServiceImpl extends ServiceImpl<DateArchiveMapper, DateArchive> implements DateArchiveService {
    
    @Autowired
    private DateArchiveMapper dateArchiveMapper;
    
    @Autowired
    private RedisUtil redisUtil;
    
    @Override
    public List<DateArchive> getListOrCache() {
        
        String key = Constants.RedisKey.DATEARCHIVE;
        if (redisUtil.hasKey(key)) {
            return JSONUtil.toList(redisUtil.get(key).toString(),DateArchive.class);
        }
        
        QueryWrapper<DateArchive> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time");
        List<DateArchive> dateArchives = dateArchiveMapper.selectList(wrapper);
        redisUtil.set(key,JSONUtil.toJsonStr(dateArchives));
        return dateArchives;
    }
}
