package com.pearadmin.boke.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pearadmin.boke.entry.Menu;
import com.pearadmin.boke.service.MenuService;
import com.pearadmin.boke.vo.query.PageVo;
import com.lsh.mapper.boke.MenuMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
* Menu接口实现层
*
* @author lushao
* @version 1.0.0 2022-01-18 08:18:23
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public IPage<Menu> menuLisPage(PageVo vo) {
        Page<Menu> page = new Page<>(vo.getPage(),vo.getLimit());
        return menuMapper.menuLisPage(page);
    }

    @Override
    public List<Menu> allUseMenuList() {
        return menuMapper.allUseMenuList();
    }
}