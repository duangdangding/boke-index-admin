package com.pearadmin.boke.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pearadmin.boke.entry.Classify;
import com.pearadmin.boke.entry.FrantPage;
import com.lsh.mapper.boke.FrantPageMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pearadmin.boke.service.FrantPageService;

/**
* FrantPage接口实现层
*
* @author lushao
* @version 1.0.0 2022-01-17 13:54:52
*/
@Service
public class FrantPageServiceImpl extends ServiceImpl<FrantPageMapper, FrantPage> implements FrantPageService {
    
    @Autowired
    private FrantPageMapper frantPageMapper;

    @Override
    public List<Classify> groupFrants() {
        return frantPageMapper.groupFrants();
    }

    @Override
    public IPage<FrantPage> allList(FrantPage vo) {
        Page<FrantPage> page = new Page<>(vo.getPage(),vo.getLimit());
        return frantPageMapper.allList(page,vo);
    }
}