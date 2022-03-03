package com.pearadmin.boke.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsh.mapper.boke.VisitsMapper;
import com.pearadmin.boke.entry.Visits;
import com.pearadmin.boke.service.VisitsService;
import com.pearadmin.boke.vo.query.QueryVisitVo;

@Service
public class VisitsServiceImpl extends ServiceImpl<VisitsMapper, Visits> implements VisitsService {
    
    @Autowired
    private VisitsMapper visitsMapper;
    
    @Override
    public IPage<Visits> getVisitPage(QueryVisitVo vo) {
        Page page = new Page(vo.getPage(),vo.getLimit());
        return visitsMapper.getVisitPage(page, vo);
    }
}
