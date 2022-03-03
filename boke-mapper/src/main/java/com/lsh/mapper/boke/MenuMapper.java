package com.lsh.mapper.boke;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pearadmin.boke.entry.Menu;

/**
* Menu数据接口层
*
* @author lushao
* @version 1.0.0 2022-01-18 08:18:23
*/
public interface MenuMapper extends BaseMapper<Menu> {

    IPage<Menu> menuLisPage(Page page);
    
    List<Menu> allUseMenuList();
    
}