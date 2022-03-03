package com.pearadmin.boke.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pearadmin.boke.entry.Classify;
import com.lsh.mapper.boke.ClassifyMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pearadmin.boke.service.ClassifyService;

/**
* Classify接口实现层
*
* @author lushao
* @version 1.0.0 2022-01-17 13:54:52
*/
@Service
public class ClassifyServiceImpl extends ServiceImpl<ClassifyMapper, Classify> implements ClassifyService {
    
    @Autowired
    private ClassifyMapper classifyMapper;

    @Override
    public List<Classify> useList() {
        QueryWrapper<Classify> wrapper = new QueryWrapper<>();
        wrapper.eq("delete_state",1);
        return classifyMapper.selectList(wrapper);
    }
}