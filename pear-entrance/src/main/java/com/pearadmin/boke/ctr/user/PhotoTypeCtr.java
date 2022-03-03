package com.pearadmin.boke.ctr.user;

import com.pearadmin.boke.service.PhotoTypeService;
import com.pearadmin.boke.vo.SelectVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author lushao
 * @Description 相册类型
 * @Date 2021/6/9 10:43
 * @Version 1.0
 */
@RestController
public class PhotoTypeCtr {
    
    @Autowired
    private PhotoTypeService photoTypeService;
    
    @RequestMapping("/pt/all")
    private List<SelectVo> getAll() {
        return photoTypeService.selects();
    }
}
