package com.pearadmin.boke.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pearadmin.boke.entry.FriendUrl;
import com.pearadmin.boke.vo.query.PageVo;

import java.util.List;

public interface YoulianService extends IService<FriendUrl> {

    List<FriendUrl> getShow();
    
    IPage<FriendUrl> getFriendPage(PageVo vo);
    
}
