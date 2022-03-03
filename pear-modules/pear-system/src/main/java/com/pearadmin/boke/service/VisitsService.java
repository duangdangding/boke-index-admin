package com.pearadmin.boke.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pearadmin.boke.entry.Visits;
import com.pearadmin.boke.vo.query.QueryVisitVo;

public interface VisitsService extends IService<Visits> {
    
    IPage<Visits> getVisitPage(QueryVisitVo vo);
}
