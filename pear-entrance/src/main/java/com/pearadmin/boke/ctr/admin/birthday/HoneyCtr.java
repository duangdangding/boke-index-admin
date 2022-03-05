package com.pearadmin.boke.ctr.admin.birthday;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lsh.birthday.Honey;
import com.pearadmin.birthday.service.HoneyService;
import com.pearadmin.boke.utils.contains.BaseCtr;
import com.pearadmin.boke.vo.ResultDto;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("admin/birthday/honey")
public class HoneyCtr extends BaseCtr {
    
    @Autowired
    private HoneyService honeyService;

    /**
     * Describe: 获取生日其他设置视图
     * Param ModelAndView
     * Return 生日其他设置视图
     */
    @GetMapping("main")
    @ApiOperation(value = "获取生日其他设置视图")
    @PreAuthorize("hasPermission('/admin/birthday/honey/main','sys:honey:main')")
    public ModelAndView main() {
        return getView(ADMINPATHPRE + "birthday/other");
    }
    
    @RequestMapping("/add")
    @PreAuthorize("hasPermission('/admin/birthday/honey/add','sys:honey:add')")
    public ResultDto addHoney(Honey honey) {
        boolean save = honeyService.save(honey);
        return returnDto(save);
    }
    
    @RequestMapping("/update")
    @PreAuthorize("hasPermission('/admin/birthday/honey/update','sys:honey:update')")
    public ResultDto updateHoney(String honey1,String honey2) {
        UpdateWrapper<Honey> wrapper = new UpdateWrapper();
        wrapper.eq("h_name",honey1).set("h_name",honey2);
        boolean save = honeyService.update(wrapper);
        return returnDto(save);
    }
    
    @RequestMapping("/get")
    @PreAuthorize("hasPermission('/admin/birthday/honey/get','sys:honey:get')")
    public ResultDto getHoney() {
        Honey one = honeyService.getOne(null);
        return success(one.gethName());
    }
    
    
}
