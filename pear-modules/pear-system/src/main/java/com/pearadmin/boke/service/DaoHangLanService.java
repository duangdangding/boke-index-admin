package com.pearadmin.boke.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pearadmin.boke.entry.Navigation;
import com.pearadmin.boke.vo.NavAndIcon;
import com.pearadmin.boke.vo.NavDto;
import com.pearadmin.system.domain.SysUser;

public interface DaoHangLanService extends IService<Navigation> {

    List<NavDto> getNavList();
    
    NavAndIcon getInfo(Integer userId);
    
    boolean updateNavTitle(String navigation);

    NavAndIcon indexNav(SysUser sysUser);
}
