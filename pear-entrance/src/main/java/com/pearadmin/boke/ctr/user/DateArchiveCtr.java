package com.pearadmin.boke.ctr.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pearadmin.boke.service.DateArchiveService;
import com.pearadmin.boke.entry.DateArchive;
import com.pearadmin.boke.utils.contains.BaseCtr;
import com.pearadmin.boke.vo.ResultDto;

@RestController
@RequestMapping("/archive")
public class DateArchiveCtr extends BaseCtr {
    
    @Autowired
    private DateArchiveService dateArchiveService;
    
    @RequestMapping("/get")
    public ResultDto getArchive() {
        List<DateArchive> list = dateArchiveService.getListOrCache();
        return success(list);
    }
    
}
