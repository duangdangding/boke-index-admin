package com.pearadmin.boke.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pearadmin.boke.entry.Classify;
import com.pearadmin.boke.entry.FrantPage;

/**
* FrantPage接口层
*
* @author lushao
* @version 1.0.0 2022-01-17 13:54:52
*/
public interface FrantPageService extends IService<FrantPage> {

    List<Classify> groupFrants();
    
    IPage<FrantPage> allList(FrantPage vo);
    
}