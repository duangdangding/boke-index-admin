package com.pearadmin.boke.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pearadmin.boke.entry.SongList;

/**
* SongList接口层
*
* @author lushao
* @version 1.0.0 2021-06-16 09:43:56
*/
public interface SongListService extends IService<SongList> {

    SongList getByUserId(Integer userId);
    
    int updateByUserId(SongList song);

}