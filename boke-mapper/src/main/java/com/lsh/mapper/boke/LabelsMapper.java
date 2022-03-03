package com.lsh.mapper.boke;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pearadmin.boke.entry.Labels;

public interface LabelsMapper extends BaseMapper<Labels> {
    
    List<Labels> getByUserId(Integer userId);
    List<Labels> labelRank();
    
    int getLabelCountByBoke(Integer labelId);
}
