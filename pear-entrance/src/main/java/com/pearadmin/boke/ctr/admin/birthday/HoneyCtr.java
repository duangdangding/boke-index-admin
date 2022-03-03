package com.pearadmin.boke.ctr.admin.birthday;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lsh.birthday.Honey;
import com.pearadmin.birthday.service.HoneyService;
import com.pearadmin.boke.utils.contains.BaseCtr;
import com.pearadmin.boke.vo.ResultDto;

@RestController
@RequestMapping("admin/birthday/honey")
public class HoneyCtr extends BaseCtr {
    
    @Autowired
    private HoneyService honeyService;
    
    @RequestMapping("/add")
    public ResultDto addHoney(Honey honey) {
        boolean save = honeyService.save(honey);
        return returnDto(save);
    }
    
    @RequestMapping("/update")
    public ResultDto updateHoney(String honey1,String honey2) {
        UpdateWrapper<Honey> wrapper = new UpdateWrapper();
        wrapper.eq("h_name",honey1).set("h_name",honey2);
        boolean save = honeyService.update(wrapper);
        return returnDto(save);
    }
    
    @RequestMapping("/get")
    public ResultDto getHoney() {
        Honey one = honeyService.getOne(null);
        return success(one.gethName());
    }
    
    
}
