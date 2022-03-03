package com.pearadmin.boke.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pearadmin.boke.entry.Comments;

import java.util.List;

public interface CommentsService extends IService<Comments> {

    /**
     * 查询评论和回复
     * @return
     */
    List<Comments> getCommentsByBokeId(Integer bokeId);
    IPage<Comments> getCommentsByBidAndPage(Integer bokeId,int page);
    
    int saveComment(Comments comments);

}
