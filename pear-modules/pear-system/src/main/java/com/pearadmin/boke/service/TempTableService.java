package com.pearadmin.boke.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pearadmin.boke.entry.TempTable;

/**
* TempTable接口层
*
* @author lushao
* @version 1.0.0 2021-07-27 15:31:04
*/
public interface TempTableService extends IService<TempTable> {

    int deleteByData(String data);
}