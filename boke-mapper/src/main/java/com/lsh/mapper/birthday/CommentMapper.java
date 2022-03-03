package com.lsh.mapper.birthday;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsh.birthday.Comment;

public interface CommentMapper extends BaseMapper<Comment> {
    
    IPage<Comment> selectByPage1(Page<Comment> page);
}
