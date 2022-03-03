package com.pearadmin.boke.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pearadmin.boke.entry.QqLoginInfo;

/**
* QqLoginInfo接口层
*
* @author lushao
* @version 1.0.0 2021-07-25 12:00:08
*/
public interface QqLoginInfoService extends IService<QqLoginInfo> {
    
    QqLoginInfo isExsitByQQNum(String qqnum);

}