package com.pearadmin.boke.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

public class BaseImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

}
