package com.pearadmin.boke.service;

import java.io.InputStream;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pearadmin.boke.entry.FileRecode;
import com.pearadmin.boke.vo.query.QueryFileRecodeVo;

public interface FileRecodeService extends IService<FileRecode> {
    
    int deleteByUrls(String[] keys);
    int deleteByUrl(String key);
    
    IPage<FileRecode> getRecodePage(QueryFileRecodeVo vo);
    
    int deleteRecodeByUrl(String urls);

    void saveUploadRecode(MultipartFile file, Map<String,String> showUrl, Integer own, Integer uploadType,Long userId);

    void saveUploadRecode(InputStream inputStream, String key, Map<String,String> showUrl, Integer own, Integer uploadType,Long userId);
}
