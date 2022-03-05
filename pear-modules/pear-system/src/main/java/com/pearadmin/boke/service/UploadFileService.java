package com.pearadmin.boke.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface UploadFileService {

    Map<String,Object> uploadPhotoReturnPath(MultipartFile image, Integer photoType,Long userId);

    void deleteByKeys(Object keys,Integer type,Long userId);
    void deleteByKey(String keys,Integer type,Long userId);
}
