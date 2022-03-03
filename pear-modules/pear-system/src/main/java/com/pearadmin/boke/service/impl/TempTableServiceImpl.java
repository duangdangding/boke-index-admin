package com.pearadmin.boke.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsh.mapper.boke.TempTableMapper;
import com.pearadmin.boke.entry.TempTable;
import com.pearadmin.boke.service.TempTableService;

/**
* TempTable接口实现层
*
* @author lushao
* @version 1.0.0 2021-07-27 15:31:04
*/
@Service
public class TempTableServiceImpl extends ServiceImpl<TempTableMapper, TempTable> implements TempTableService {
    
    @Autowired
    private TempTableMapper tempTableMapper;

    @Override
    public int deleteByData(String data) {
        QueryWrapper<TempTable> wrapper = new QueryWrapper<>();
        wrapper.eq("temp_data",data);
        return tempTableMapper.delete(wrapper);
    }
}