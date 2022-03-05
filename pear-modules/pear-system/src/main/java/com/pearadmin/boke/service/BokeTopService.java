package com.pearadmin.boke.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pearadmin.boke.entry.BokeTop;

public interface BokeTopService extends IService<BokeTop> {
    
    List<BokeTop> getByuserId(Long userId);

    int getByuserIdCount(Long userId);
}
