package com.pearadmin.boke.ctr.admin;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pearadmin.boke.service.FileRecodeService;
import com.pearadmin.boke.entry.FileRecode;
import com.pearadmin.boke.utils.contains.BaseCtr;
import com.pearadmin.boke.vo.ResultDto;
import com.pearadmin.boke.vo.query.QueryFileRecodeVo;

@RestController
@RequestMapping("admin/recode")
public class FileRecodeCtr extends BaseCtr {
    
    @Autowired
    private FileRecodeService fileRecodeService;
    
    @RequestMapping("/page")
    public Map<String,Object> getRecodePage(QueryFileRecodeVo vo) {
        IPage<FileRecode> recodePage = fileRecodeService.getRecodePage(vo);
        return convertLayuiPage(recodePage);
    }
    
    @RequestMapping("/delete")
    public ResultDto recodeDelete(String urls) {
        int i = fileRecodeService.deleteRecodeByUrl(urls);
        return returnDto(i);
    }
}
