package com.pearadmin.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pearadmin.common.tools.SequenceUtil;
import com.pearadmin.common.web.domain.request.PageDomain;
import com.pearadmin.system.domain.SysRole;
import com.pearadmin.system.domain.SysUser;
import com.pearadmin.system.domain.SysUserRole;
import com.pearadmin.system.mapper.SysRoleMapper;
import com.pearadmin.system.mapper.SysUserMapper;
import com.pearadmin.system.mapper.SysUserRoleMapper;
import com.pearadmin.system.service.ISysUserService;

/**
 * Describe: 用户服务实现类
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 */
@Service
public class SysUserServiceImpl implements ISysUserService {

    /**
     * 注入用户服务
     */
    @Resource
    private SysUserMapper sysUserMapper;

    /**
     * 注入用户角色服务
     */
    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    /**
     * 注入角色服务
     */
    @Resource
    private SysRoleMapper sysRoleMapper;




    /**
     * Describe: 根据条件查询用户列表数据
     * Param: username
     * Return: 返回用户列表数据
     */
    @Override
    public List<SysUser> list(SysUser param) {
        return sysUserMapper.selectList(param);
    }

    /**
     * Describe: 根据条件查询用户列表数据  分页
     * Param: username
     * Return: 返回分页用户列表数据
     */
    @Override
    public IPage<SysUser> page(SysUser param, PageDomain pageDomain) {
        Page<SysUser> page = new Page<>(pageDomain.getPage(), pageDomain.getLimit());
        return sysUserMapper.selectList(page,param);
    }
/*
    @Override
    public PageInfo<SysUser> page(SysUser param, PageDomain pageDomain) {
        PageHelper.startPage(pageDomain.getPage(), pageDomain.getLimit());
        List<SysUser> sysUsers = sysUserMapper.selectList(param);
        return new PageInfo<>(sysUsers);
    }
*/

    /**
     * Describe: 根据 ID 查询用户
     * Param: id
     * Return: 返回用户信息
     */
    @Override
    public SysUser getById(String id) {
        return sysUserMapper.selectById(id);
    }

    /**
     * Describe: 根据 id 删除用户数据
     * Param: id
     * Return: Boolean
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean remove(String id) {
        sysUserRoleMapper.deleteByUserId(id);
        sysUserMapper.deleteById(id);
        return true;
    }

    /**
     * Describe: 根据 id 批量删除用户数据
     * Param: ids
     * Return: Boolean
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchRemove(String[] ids) {
        sysUserMapper.deleteByIds(ids);
        sysUserRoleMapper.deleteByUserIds(ids);
        return true;
    }

    /**
     * Describe: 保存用户数据
     * Param: SysUser
     * Return: 操作结果
     */
    @Override
    public boolean save(SysUser sysUser) {
        SysUser user = new SysUser();
        user.setUsername(sysUser.getUsername());
        if (sysUserMapper.count(user) > 0) {
            return false;
        }
        int result = sysUserMapper.insert(sysUser);
        return result > 0;
    }

    /**
     * Describe: 修改用户数据
     * Param: SysUser
     * Return: 操作结果
     */
    @Override
    public boolean update(SysUser sysUser) {
        Integer result = sysUserMapper.updateById(sysUser);
        return result > 0;
    }

    /**
     * Describe: 保存用户角色数据
     * Param: SysUser
     * Return: 操作结果
     */
    @Override
    public boolean saveUserRole(String userId, List<String> roleIds) {
        sysUserRoleMapper.deleteByUserId(userId);
        List<SysUserRole> sysUserRoles = new ArrayList<>();
        roleIds.forEach(roleId -> {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setId(SequenceUtil.makeStringId());
            sysUserRole.setRoleId(roleId);
            sysUserRole.setUserId(userId);
            sysUserRoles.add(sysUserRole);
        });
        int i = sysUserRoleMapper.batchInsert(sysUserRoles);
        return i > 0;
    }

    /**
     * Describe: 获取
     * Param: SysUser
     * Return: 操作结果
     */
    @Override
    public List<SysRole> getUserRole(String userId) {
        List<SysRole> allRole = sysRoleMapper.selectList(null);
        List<SysUserRole> myRole = sysUserRoleMapper.selectByUserId(userId);
        allRole.forEach(sysRole -> {
            myRole.forEach(sysUserRole -> {
                if (sysRole.getRoleId().equals(sysUserRole.getRoleId())) {
                    sysRole.setChecked(true);
                }
            });
        });
        return allRole;
    }



}
