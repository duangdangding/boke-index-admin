package com.pearadmin.boke.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pearadmin.boke.entry.BokeTop;

import java.util.List;

public interface BokeTopService extends IService<BokeTop> {
    
    List<BokeTop> getByuserId(Integer userId);

    int getByuserIdCount(Integer userId);
}
