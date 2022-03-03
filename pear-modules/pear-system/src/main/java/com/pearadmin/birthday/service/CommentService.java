package com.pearadmin.birthday.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lsh.birthday.Comment;
import com.pearadmin.boke.vo.query.PageVo;

public interface CommentService extends IService<Comment> {

    IPage<Comment> getPage(PageVo vo);
    
}
