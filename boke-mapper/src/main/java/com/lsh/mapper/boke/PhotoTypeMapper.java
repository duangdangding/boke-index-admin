package com.lsh.mapper.boke;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pearadmin.boke.entry.PhotoType;

public interface PhotoTypeMapper extends BaseMapper<PhotoType> {
    
    List<PhotoType> getByUseType();
}
