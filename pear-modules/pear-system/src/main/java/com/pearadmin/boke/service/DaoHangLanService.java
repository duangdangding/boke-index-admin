package com.pearadmin.boke.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pearadmin.boke.entry.Navigation;
import com.pearadmin.boke.vo.NavAndIcon;
import com.pearadmin.boke.vo.NavDto;

import java.util.List;

public interface DaoHangLanService extends IService<Navigation> {

    List<NavDto> getNavList();
    
    NavAndIcon getInfo(Integer userId);
    
    boolean updateNavTitle(String navigation);
}
