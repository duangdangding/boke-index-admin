package com.pearadmin.boke.ctr.admin;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pearadmin.boke.entry.Visits;
import com.pearadmin.boke.service.VisitsService;
import com.pearadmin.boke.utils.contains.BaseCtr;
import com.pearadmin.boke.vo.query.QueryVisitVo;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("admin/visit")
public class VisitCtr extends BaseCtr {
    
    @Autowired
    private VisitsService visitsService;

    /**
     * Describe: 获取访客列表视图
     * Param ModelAndView
     * Return 访客列表视图
     */
    @GetMapping("main")
    @ApiOperation(value = "获取访客列表视图")
    @PreAuthorize("hasPermission('/admin/visit/main','sys:visit:main')")
    public ModelAndView main() {
        return getView(ADMINPATHPRE + "visit");
    }
    
    @RequestMapping("/page")
    @PreAuthorize("hasPermission('/admin/visit/page','sys:visit:page')")
    public Map<String,Object> visitPage(QueryVisitVo vo) {
        IPage<Visits> visitPage = visitsService.getVisitPage(vo);
        return convertLayuiPage(visitPage);
    }
    
}
