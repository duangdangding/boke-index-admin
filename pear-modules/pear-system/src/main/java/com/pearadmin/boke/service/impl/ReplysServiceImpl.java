package com.pearadmin.boke.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsh.mapper.boke.ReplysMapper;
import com.pearadmin.boke.entry.Replys;
import com.pearadmin.boke.service.ReplysService;
import com.pearadmin.boke.vo.query.QueryCommontVo;

import cn.hutool.core.util.StrUtil;

@Service
public class ReplysServiceImpl extends ServiceImpl<ReplysMapper, Replys> implements ReplysService {
    
    @Autowired
    private ReplysMapper replysMapper;
    
    @Override
    public IPage<Replys> getReplayPage(QueryCommontVo vo) {
        Page<Replys> page = new Page<>(vo.getPage(),vo.getLimit());
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.orderByDesc("reply_time");
        return replysMapper.selectPage(page,wrapper);
    }

    @Override
    public int deleteByIds(String ids) {
        if (StrUtil.isNotEmpty(ids)) {
            String[] split = ids.split(",");
            List<Integer> idList = Arrays.stream(split)
                    .map(s->Integer.parseInt(s.trim()))
                    .collect(Collectors.toList());
            return replysMapper.deleteBatchIds(idList);
        }
        return 0;
    }
}
