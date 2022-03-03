package com.pearadmin.birthday.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsh.birthday.Honey;
import com.lsh.mapper.birthday.HoneyMapper;
import com.pearadmin.birthday.service.HoneyService;

@DS("slave_1")
@Service
public class HoneyServiceImpl extends ServiceImpl<HoneyMapper, Honey> implements HoneyService {

}
