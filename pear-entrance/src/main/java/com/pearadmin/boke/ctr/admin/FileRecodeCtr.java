package com.pearadmin.boke.ctr.admin;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pearadmin.boke.entry.FileRecode;
import com.pearadmin.boke.service.FileRecodeService;
import com.pearadmin.boke.utils.contains.BaseCtr;
import com.pearadmin.boke.vo.ResultDto;
import com.pearadmin.boke.vo.query.QueryFileRecodeVo;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("admin/recode")
public class FileRecodeCtr extends BaseCtr {
    
    @Autowired
    private FileRecodeService fileRecodeService;

    /**
     * Describe: 获取文件上传记录列表视图
     * Param ModelAndView
     * Return 文件上传记录列表视图
     */
    @GetMapping("page")
    @ApiOperation(value = "获取文件上传记录列表视图")
    @PreAuthorize("hasPermission('/admin/recode/page','sys:recode:main')")
    public ModelAndView main() {
        return getView(ADMINPATHPRE + "uploadm");
    }
    
    @RequestMapping("/list")
    @PreAuthorize("hasPermission('/admin/recode/list','sys:recode:list')")
    public Map<String,Object> getRecodePage(QueryFileRecodeVo vo) {
        IPage<FileRecode> recodePage = fileRecodeService.getRecodePage(vo);
        return convertLayuiPage(recodePage);
    }
    
    @RequestMapping("/delete")
    @PreAuthorize("hasPermission('/admin/recode/delete','sys:recode:delete')")
    public ResultDto recodeDelete(String urls) {
        int i = fileRecodeService.deleteRecodeByUrl(urls);
        return returnDto(i);
    }
}
