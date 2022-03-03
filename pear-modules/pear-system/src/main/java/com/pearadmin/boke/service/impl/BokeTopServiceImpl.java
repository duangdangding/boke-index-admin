package com.pearadmin.boke.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pearadmin.boke.entry.BokeTop;
import com.lsh.mapper.boke.BokeTopMapper;
import com.pearadmin.boke.service.BokeTopService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BokeTopServiceImpl extends ServiceImpl<BokeTopMapper, BokeTop> implements BokeTopService {
    
    @Autowired
    private BokeTopMapper bokeTopMapper;
    
    @Override
    public List<BokeTop> getByuserId(Integer userId) {
        QueryWrapper<BokeTop> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        List<BokeTop> bokeTops = bokeTopMapper.selectList(wrapper);
        return bokeTops;
    }

    @Override
    public int getByuserIdCount(Integer userId) {
        return bokeTopMapper.getByuserIdCount(userId);
    }
}
