package com.pearadmin.boke.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsh.mapper.boke.UserCustomMapper;
import com.pearadmin.boke.entry.UserCustom;
import com.pearadmin.boke.service.UserCustomService;

/**
* UserCustom接口实现层
*
* @author lushao
* @version 1.0.0 2021-06-16 11:35:59
*/
@Service
public class UserCustomServiceImpl extends ServiceImpl<UserCustomMapper, UserCustom> implements UserCustomService {

}