package com.lsh.mapper.boke;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pearadmin.boke.entry.Visits;
import com.pearadmin.boke.vo.query.QueryVisitVo;

public interface VisitsMapper extends BaseMapper<Visits> {
    
    IPage<Visits> getVisitPage(Page page, @Param("vo") QueryVisitVo vo);
}
