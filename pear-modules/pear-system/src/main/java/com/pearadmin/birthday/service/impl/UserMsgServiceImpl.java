package com.pearadmin.birthday.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsh.birthday.UserMsg;
import com.pearadmin.birthday.service.UserMsgService;
import com.pearadmin.boke.vo.query.PageVo;
import com.lsh.mapper.birthday.UserMsgMapper;

@DS("slave_1")
@Service
public class UserMsgServiceImpl extends ServiceImpl<UserMsgMapper, UserMsg> implements UserMsgService {
    
    @Autowired
    private UserMsgMapper userMsgMapper;
    
    @Override
    public IPage<UserMsg> getPage(PageVo vo) {
        Page<UserMsg> page = new Page<>(vo.getPage(),vo.getLimit());
        QueryWrapper<UserMsg> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("login_time");
        return userMsgMapper.selectPage(page,wrapper);
    }
}
