package com.pearadmin.boke.ctr.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pearadmin.boke.service.DaoHangLanService;
import com.pearadmin.boke.entry.Navigation;
import com.pearadmin.boke.utils.contains.BaseCtr;
import com.pearadmin.boke.vo.ResultDto;

@RestController
@RequestMapping("admin/navigation")
public class NavigationCtr extends BaseCtr {
    
    @Autowired
    private DaoHangLanService daoHangLanService;
    
    @RequestMapping("/page")
    public Map<String,Object> getNavPage() {
        List<Navigation> list = daoHangLanService.list();
        return convertLayuiPage(list);
    }
    
    @RequestMapping("/upOrAdd")
    public ResultDto upOrAddNav(Navigation navigation) {
        boolean b = daoHangLanService.saveOrUpdate(navigation);
        return returnDto(b);
    }
    
    @RequestMapping("/update")
    public ResultDto subTitleUpdate(String subTitle) {
        boolean b = daoHangLanService.updateNavTitle(subTitle);
        return returnDto(b);
    }
}
