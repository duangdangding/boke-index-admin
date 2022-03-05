package com.pearadmin.boke.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pearadmin.boke.entry.Photos;

public interface PhotosService extends IService<Photos> {
    
    IPage<Photos> getPhotosList(int pageNumber,Integer photoType);
    
    int deleteByKeys(String[] keys);
    int deleteByKey(String keys);
    
    int setDeleteStateByUrl(String url);
    
    Photos getByUrl(String url);
    Photos getByUrlAndUser(String url,Long userId);

}
