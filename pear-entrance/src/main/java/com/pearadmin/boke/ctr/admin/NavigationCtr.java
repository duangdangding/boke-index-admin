package com.pearadmin.boke.ctr.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.pearadmin.boke.entry.Navigation;
import com.pearadmin.boke.service.DaoHangLanService;
import com.pearadmin.boke.utils.contains.BaseCtr;
import com.pearadmin.boke.vo.ResultDto;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("admin/navigation")
public class NavigationCtr extends BaseCtr {
    
    @Autowired
    private DaoHangLanService daoHangLanService;

    /**
     * Describe: 获取首页导航列表视图
     * Param ModelAndView
     * Return 首页导航列表视图
     */
    @GetMapping("main")
    @ApiOperation(value = "获取首页导航列表视图")
    @PreAuthorize("hasPermission('/admin/navigation/main','sys:navigation:main')")
    public ModelAndView commont() {
        return getView(ADMINPATHPRE + "navigation");
    }
    
    @RequestMapping("/page")
    @PreAuthorize("hasPermission('/admin/navigation/page','sys:navigation:page')")
    public Map<String,Object> getNavPage() {
        List<Navigation> list = daoHangLanService.list();
        return convertLayuiPage(list);
    }
    
    //<ul class="ed_type"><li><a href="/t/wd">富文本编辑器</a></li><li><a href="/t/md">markdown编辑器</a></li></ul>
    @RequestMapping("/upOrAdd")
    @PreAuthorize("hasPermission('/admin/navigation/upOrAdd','sys:navigation:upOrAdd')")
    public ResultDto upOrAddNav(Navigation navigation) {
        boolean b = daoHangLanService.saveOrUpdate(navigation);
        return returnDto(b);
    }

    /**
     * 更新副标题
     * @param subTitle
     * @return
     */
    @RequestMapping("/update")
    @PreAuthorize("hasPermission('/admin/navigation/update','sys:navigation:update')")
    public ResultDto subTitleUpdate(String subTitle) {
        boolean b = daoHangLanService.updateNavTitle(subTitle);
        return returnDto(b);
    }
    @RequestMapping("/setShowStatus")
    @PreAuthorize("hasPermission('/admin/navigation/setShowStatus','sys:navigation:update')")
    public ResultDto setShowStatus(Integer navShow,Integer navId) {
        UpdateWrapper<Navigation> wrapper = new UpdateWrapper<>();
        wrapper.set("nav_show",navShow).eq("nav_id",navId);
        boolean b = daoHangLanService.update(wrapper);
        return returnDto(b);
    }
}
