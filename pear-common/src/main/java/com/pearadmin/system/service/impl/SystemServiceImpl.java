package com.pearadmin.system.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pearadmin.system.domain.SysLog;
import com.pearadmin.system.domain.SysRole;
import com.pearadmin.system.domain.SysUser;
import com.pearadmin.system.mapper.SysPowerMapper;
import com.pearadmin.system.mapper.SysRoleMapper;
import com.pearadmin.system.mapper.SysUserMapper;
import com.pearadmin.system.service.ISysLogService;
import com.pearadmin.system.service.SystemService;

/**
 * Describe: 对外开放的公用服务
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 */
@Service
public class SystemServiceImpl implements SystemService {

    @Resource
    private ISysLogService sysLogService;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SysPowerMapper sysPowerMapper;


    @Override
    public SysUser getUserByName(String username) {
        SysUser sysUser = sysUserMapper.selectByUsername(username);
        if (sysUser != null) {
            sysUser.setPowerList(sysPowerMapper.selectByUsername(username));
        }
        return sysUser;
    }

    @Override
    public SysUser getUserById(Long id) {
        return sysUserMapper.selectById(id);
    }

    @Override
    public List<SysRole> getRolesByUsername(String username) {
        return sysRoleMapper.selectByUsername(username);
    }

    @Override
    public Boolean saveLog(SysLog log) {
        return sysLogService.save(log);
    }
}
