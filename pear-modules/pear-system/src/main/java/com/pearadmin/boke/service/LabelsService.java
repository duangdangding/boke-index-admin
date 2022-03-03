package com.pearadmin.boke.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pearadmin.boke.entry.Labels;
import com.pearadmin.boke.vo.query.QueryLabelVo;

public interface LabelsService extends IService<Labels> {

    List<Labels> getByUserId(Integer userId);
    
    IPage<Labels> getLabelPage(QueryLabelVo vo);
    
    Integer getByLabelName(String labelName);

    List<Labels> labelRank();

    int getLabelCountByBoke(Integer labelId);
    
    int setDeleteState(String ids,Integer deleteState);
    
    List<Labels> getUseLabels();
}
