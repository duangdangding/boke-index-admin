package com.pearadmin.boke.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pearadmin.boke.entry.Menu;
import com.pearadmin.boke.vo.query.PageVo;

/**
* Menu接口层
*
* @author lushao
* @version 1.0.0 2022-01-18 08:18:23
*/
public interface MenuService extends IService<Menu> {
    
    IPage<Menu> menuLisPage(PageVo vo);

    List<Menu> allUseMenuList();
    
}