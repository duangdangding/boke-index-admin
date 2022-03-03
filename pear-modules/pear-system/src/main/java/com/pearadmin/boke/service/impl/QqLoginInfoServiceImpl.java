package com.pearadmin.boke.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsh.mapper.boke.QqLoginInfoMapper;
import com.pearadmin.boke.entry.QqLoginInfo;
import com.pearadmin.boke.service.QqLoginInfoService;

/**
* QqLoginInfo接口实现层
*
* @author lushao
* @version 1.0.0 2021-07-25 12:00:08
*/
@Service
public class QqLoginInfoServiceImpl extends ServiceImpl<QqLoginInfoMapper, QqLoginInfo> implements QqLoginInfoService {
    
    @Autowired
    private QqLoginInfoMapper qqLoginInfoMapper;

    @Override
    public QqLoginInfo isExsitByQQNum(String qqnum) {
        QueryWrapper<QqLoginInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("qq_num",qqnum);
        return qqLoginInfoMapper.selectOne(wrapper);
    }
}