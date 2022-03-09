package com.pearadmin.secure.support;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.pearadmin.boke.entry.Bokes;
import com.pearadmin.boke.service.BokesService;
import com.pearadmin.common.config.proprety.SecurityProperty;
import com.pearadmin.system.domain.SysPower;
import com.pearadmin.system.domain.SysUser;

/**
 * Describe: 自定义 Security 权限注解实现
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 */
@Component
public class SecurePermissionSupport implements PermissionEvaluator {

    @Resource
    private SecurityProperty securityProperty;
    
    @Autowired
    private BokesService bokesService;

    /**
     * Describe: 自定义 Security 权限认证 @hasPermission
     * Param: Authentication
     * Return Boolean
     */
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        Object principal = authentication.getPrincipal();
        if ("anonymousUser".equals(principal)) {
            return false;
        }
        SysUser securityUserDetails = (SysUser) principal;
        String username = securityUserDetails.getUsername();
        Long userId = securityUserDetails.getUserId();
        String s = targetDomainObject.toString();
        if ("sys:boke:editor".equals(permission) && s.contains("/t/boke/editor")) {
            String replace = s.replace("/t/boke/editor/","");
            Bokes bokes = bokesService.selectByUidAndBid(userId, Long.valueOf(replace));
            return "葵花籽儿".equals(username) || bokes != null;
        }
        
        if (securityProperty.isSuperAuthOpen() && securityProperty.getSuperAdmin().equals(username)) {
            return true;
        }
        List<SysPower> powerList = securityUserDetails.getPowerList();
        Set<String> permissions = new HashSet<>();
        for (SysPower sysPower : powerList) {
            permissions.add(sysPower.getPowerCode());
        }
        return permissions.contains(permission);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
