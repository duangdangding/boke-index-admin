package com.pearadmin.boke.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsh.mapper.boke.BokeTopMapper;
import com.pearadmin.boke.entry.BokeTop;
import com.pearadmin.boke.service.BokeTopService;

@Service
public class BokeTopServiceImpl extends ServiceImpl<BokeTopMapper, BokeTop> implements BokeTopService {
    
    @Autowired
    private BokeTopMapper bokeTopMapper;
    
    @Override
    public List<BokeTop> getByuserId(Long userId) {
        QueryWrapper<BokeTop> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        List<BokeTop> bokeTops = bokeTopMapper.selectList(wrapper);
        return bokeTops;
    }

    @Override
    public int getByuserIdCount(Long userId) {
        return bokeTopMapper.getByuserIdCount(userId);
    }
}
