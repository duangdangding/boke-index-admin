package com.pearadmin.secure.domain;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.pearadmin.system.domain.SysPower;
import com.pearadmin.system.domain.SysRole;
import com.pearadmin.system.domain.SysUser;
import com.pearadmin.system.mapper.SysPowerMapper;
import com.pearadmin.system.mapper.SysRoleMapper;
import com.pearadmin.system.mapper.SysUserMapper;

/**
 * Describe: Security 用户服务
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 */
@Component
public class SecureUserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysPowerMapper sysPowerMapper;
    
    @Resource
    private SysRoleMapper sysRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserMapper.selectByUsername(username);
        if (sysUser == null) {
            throw new UsernameNotFoundException("Account Not Found");
        }
        List<SysRole> rolesByUserId = sysRoleMapper.getRolesByUserId(sysUser.getUserId());
        List<String> collect = rolesByUserId.stream().map(SysRole::getRoleCode).collect(Collectors.toList());
        sysUser.setRoles(new HashSet<>(collect));
        List<SysPower> powerList = sysPowerMapper.selectByUsername(username);
        sysUser.setPowerList(powerList);
        return sysUser;
    }
}
