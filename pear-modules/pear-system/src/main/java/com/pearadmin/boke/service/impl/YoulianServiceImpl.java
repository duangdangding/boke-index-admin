package com.pearadmin.boke.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsh.mapper.boke.YoulianMapper;
import com.pearadmin.boke.entry.FriendUrl;
import com.pearadmin.boke.service.YoulianService;
import com.pearadmin.boke.vo.query.PageVo;

/**
 * @Author ycf
 * @Description $
 * @Date 15:34
 * @Version 1.0
 */
@Service
public class YoulianServiceImpl extends ServiceImpl<YoulianMapper, FriendUrl> implements YoulianService {
    
    @Autowired
    private YoulianMapper youlianMapper;
    
    @Override
    public List<FriendUrl> getShow() {
        QueryWrapper<FriendUrl> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("url_show",1);
        queryWrapper.orderByAsc("sort_num");
        return youlianMapper.selectList(queryWrapper);
    }

    @Override
    public IPage<FriendUrl> getFriendPage(PageVo vo) {
        Page<FriendUrl> page = new Page<>(vo.getPage(),vo.getLimit());
        return youlianMapper.selectPage(page,null);
    }

}
