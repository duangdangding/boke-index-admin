package com.pearadmin.boke.ctr.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pearadmin.boke.service.FrantPageService;
import com.pearadmin.boke.entry.Classify;
import com.pearadmin.boke.utils.contains.BaseCtr;
import com.pearadmin.boke.vo.ResultDto;

/**
* FrantPage控制层
*
* @author lushao
* @version 1.0.0 2022-01-17 13:54:52
*/
@RestController
@RequestMapping("/frant")
public class FrantPageCtr extends BaseCtr {

    @Autowired
    private FrantPageService frantPageService;

    /**
     * 查询所有的导航 并按照类别进行分类，并且排序展示
     * @return
     */
    /*@RequestMapping("/page")
    public ModelAndView toFrantPage() {
        List<Classify> classifies = frantPageService.groupFrants();
        Map<String,Object> map = new HashMap<>();
        map.put("classifies",classifies);
        return getView("frantPage",map);
    }*/
    
    @RequestMapping("/list")
    public ResultDto groupFrants() {
        List<Classify> classifies = frantPageService.groupFrants();
        return success(classifies);
    }
}