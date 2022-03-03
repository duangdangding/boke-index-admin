package com.pearadmin.boke.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pearadmin.boke.entry.DateArchive;

public interface DateArchiveService extends IService<DateArchive> {
    
    List<DateArchive> getListOrCache();
}
