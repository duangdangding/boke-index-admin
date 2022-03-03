package com.pearadmin.boke.ctr.admin;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pearadmin.boke.service.VisitsService;
import com.pearadmin.boke.entry.Visits;
import com.pearadmin.boke.utils.contains.BaseCtr;
import com.pearadmin.boke.vo.query.QueryVisitVo;

@RestController
@RequestMapping("admin/visit")
public class VisitCtr extends BaseCtr {
    
    @Autowired
    private VisitsService visitsService;
    
    @RequestMapping("/page")
    public Map<String,Object> visitPage(QueryVisitVo vo) {
        IPage<Visits> visitPage = visitsService.getVisitPage(vo);
        return convertLayuiPage(visitPage);
    }
    
}
