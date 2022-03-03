package com.pearadmin.boke.ctr.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pearadmin.boke.service.MenuService;
import com.pearadmin.boke.entry.Menu;
import com.pearadmin.boke.utils.contains.BaseCtr;
import com.pearadmin.boke.vo.ResultDto;
import com.pearadmin.boke.vo.SelectVo;
import com.pearadmin.boke.vo.query.PageVo;

import cn.hutool.core.collection.CollectionUtil;

/**
* Menu控制层
*
* @author lushao
* @version 1.0.0 2022-01-18 08:18:23
*/
@RestController
@RequestMapping("/menu")
public class MenuCtr extends BaseCtr {
    
    @Autowired
    private MenuService menuService;
    
    @RequestMapping("/lushao/page")
    public ModelAndView toManagement() {
        List<Menu> menus = menuService.allUseMenuList();
        Map<String,Object> map = new HashMap<>();
        map.put("menus",menus);
        return getView("boke/admin/admin",map);
    }
    
    @RequestMapping("/list")
    public Map<String,Object> getMenuList(PageVo vo) {
        IPage<Menu> menuIPage = menuService.menuLisPage(vo);
        return convertLayuiPage(menuIPage);
    }
    @RequestMapping("/select")
    public List<SelectVo> getParentMenuList() {
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper.eq("menu_parent_id",0);
        List<Menu> list = menuService.list(wrapper);
        if (CollectionUtil.isNotEmpty(list)) {
            return convertSelectVo(list);
        }
        return null;
    }
    
    @RequestMapping("/addOrEdit")
    public ResultDto addOrEdit(Menu menu) {
        boolean b = menuService.saveOrUpdate(menu);
        return returnDto(b);
    }
    
    @RequestMapping("/deleteState")
    public ResultDto deleteState(Integer menuId,int deleteState) {
        UpdateWrapper<Menu> wrapper = new UpdateWrapper<>();
        wrapper.set("delete_state",deleteState);
        wrapper.eq("menu_id",menuId);
        boolean update = menuService.update(wrapper);
        return returnDto(update);
    }
    
    @RequestMapping("/delete")
    public ResultDto delete(@RequestParam("ids") List<Long> ids) {
        boolean b = menuService.removeByIds(ids);
        return returnDto(b); 
    }


    protected List<SelectVo> convertSelectVo(List<Menu> list) {
        List<SelectVo> selectVos = new ArrayList<>();
        list.forEach(e -> {
            selectVos.add(new SelectVo(e.getMenuId(),e.getMenuName()));
        });
        return selectVos;
    }

}