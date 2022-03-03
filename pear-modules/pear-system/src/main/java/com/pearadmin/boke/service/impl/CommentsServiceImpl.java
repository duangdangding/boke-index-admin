package com.pearadmin.boke.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pearadmin.boke.entry.Comments;
import com.lsh.mapper.boke.BokesMapper;
import com.lsh.mapper.boke.CommontsMapper;
import com.pearadmin.boke.service.CommentsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CommentsServiceImpl extends ServiceImpl<CommontsMapper, Comments> implements CommentsService {
    
    @Autowired
    private CommontsMapper commentsMapper;
    
    @Autowired
    private BokesMapper bokesMapper;

    @Override
    public List<Comments> getCommentsByBokeId(Integer bokeId) {
        return commentsMapper.getCommentsByBokeId(bokeId);
    }

    @Override
    public IPage<Comments> getCommentsByBidAndPage(Integer bokeId, int pageNo) {
        Page<Comments> page = new Page<>(pageNo,20);
        return commentsMapper.getCommentsByBokeId(page,bokeId,pageNo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveComment(Comments comments) {
        try {
            commentsMapper.saveComments(comments);
            //        评论数设置到boke表
            bokesMapper.setCommentNum(comments.getBokeId());
            return 1;
        }catch (Exception e) {
            // e.printStackTrace();
            log.error("评论失败：" + e.getMessage());
            //强制手动事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 0;
        }
    }
}
