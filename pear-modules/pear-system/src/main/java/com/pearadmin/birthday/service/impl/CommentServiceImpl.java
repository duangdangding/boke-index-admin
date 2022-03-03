package com.pearadmin.birthday.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsh.birthday.Comment;
import com.pearadmin.birthday.service.CommentService;
import com.pearadmin.boke.vo.query.PageVo;
import com.lsh.mapper.birthday.CommentMapper;

@DS("slave_1")
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public IPage<Comment> getPage(PageVo vo) {
        Page<Comment> page = new Page<>(vo.getPage(),vo.getLimit());
        // QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        // wrapper.orderByDesc("createtime");
        // return commentMapper.selectPage(page, wrapper);
        return commentMapper.selectByPage1(page);
    }
}
