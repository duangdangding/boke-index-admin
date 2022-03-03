package com.pearadmin.boke.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pearadmin.boke.entry.PhotoType;
import com.pearadmin.boke.vo.SelectVo;

import java.util.List;

public interface PhotoTypeService extends IService<PhotoType> {
    
    List<SelectVo> selects();
}
