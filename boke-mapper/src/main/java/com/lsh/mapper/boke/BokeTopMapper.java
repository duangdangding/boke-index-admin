package com.lsh.mapper.boke;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pearadmin.boke.entry.BokeTop;

public interface BokeTopMapper extends BaseMapper<BokeTop> {
    
    int getByuserIdCount(Long userId);
}
