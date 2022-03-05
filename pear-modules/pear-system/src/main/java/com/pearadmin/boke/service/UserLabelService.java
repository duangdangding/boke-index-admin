package com.pearadmin.boke.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pearadmin.boke.entry.UserLabel;

/**
* UserLabel接口层
*
* @author lushao
* @version 1.0.0 2021-08-11 10:16:37
*/
public interface UserLabelService extends IService<UserLabel> {
    
    UserLabel getByUserIdLid(Integer labelId,Long userId);
    
}