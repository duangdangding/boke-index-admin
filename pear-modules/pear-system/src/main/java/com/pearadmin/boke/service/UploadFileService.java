package com.pearadmin.boke.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface UploadFileService {

    Map<String,Object> uploadPhotoReturnPath(MultipartFile image, Integer photoType);

    void deleteByKeys(Object keys,Integer type);
    void deleteByKey(String keys,Integer type);
}
