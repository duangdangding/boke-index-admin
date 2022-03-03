package com.pearadmin.boke.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsh.mapper.boke.UserLabelMapper;
import com.pearadmin.boke.entry.UserLabel;
import com.pearadmin.boke.service.UserLabelService;

/**
* UserLabel接口实现层
*
* @author lushao
* @version 1.0.0 2021-08-11 10:16:37
*/
@Service
public class UserLabelServiceImpl extends ServiceImpl<UserLabelMapper, UserLabel> implements UserLabelService {

    @Override
    public UserLabel getByUserIdLid(Integer labelId, Integer userId) {
        QueryWrapper<UserLabel> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId).eq("label_id",labelId);
        return getOne(wrapper);
    }

}