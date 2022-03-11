package com.pearadmin.boke.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsh.mapper.boke.PhotosMapper;
import com.pearadmin.boke.entry.Photos;
import com.pearadmin.boke.service.PhotosService;
import com.pearadmin.boke.utils.contains.Constants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PhotosServiceImpl extends ServiceImpl<PhotosMapper, Photos> implements PhotosService {
    
    @Autowired
    private PhotosMapper photosMapper;
    
    @Override
    public IPage<Photos> getPhotosList(int pageNumber,Integer photoType) {
        QueryWrapper<Photos> wrapper = new QueryWrapper<>();
        wrapper.eq("photo_type",photoType);
        wrapper.orderByDesc("create_time");
        wrapper.eq("delete_state",1);
        Page<Photos> page = new Page<>(pageNumber, Constants.PageSize.SIZE30);
        return photosMapper.selectPage(page,wrapper);
    }

    @Override
    public int deleteByKeys(String[] keys) {
        QueryWrapper<Photos> wrapper = new QueryWrapper<>();
        wrapper.and(wrapper2 -> wrapper2.in("oos_key",keys).or().in("photo_url",keys));
        return photosMapper.delete(wrapper);
    }

    @Override
    public int deleteByKey(String keys) {
        QueryWrapper<Photos> wrapper = new QueryWrapper<>();
        wrapper.and(wrapper2 -> wrapper2.eq("oos_key",keys).or().eq("photo_url",keys));
        return photosMapper.delete(wrapper);
    }

    @Override
    public int setDeleteStateByUrl(String url) {
        return photosMapper.setDeleteStateByUrl(url);
    }

    @Override
    public Photos getByUrl(String url) {
        QueryWrapper<Photos> wrapper = new QueryWrapper<>();
        wrapper.eq("photo_url",url);
        return photosMapper.selectOne(wrapper);
    }

    @Override
    public Photos getByUrlAndUser(String url, Long userId) {
        return photosMapper.getByUrlAndUser(url,userId);
    }

    

}
