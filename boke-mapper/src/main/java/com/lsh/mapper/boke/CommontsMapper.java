package com.lsh.mapper.boke;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pearadmin.boke.entry.Comments;

import java.util.List;

public interface CommontsMapper extends BaseMapper<Comments> {
    
    List<Comments> getCommentsByBokeId(Integer bokeId);
    IPage<Comments> getCommentsByBokeId(Page<Comments> page,Integer bokeId, int pageNo);
    
    int saveComments(Comments comments);
}
