package com.pearadmin.system.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pearadmin.boke.utils.MailUtils;
import com.pearadmin.boke.utils.RedisUtil;
import com.pearadmin.boke.utils.contains.Constants;
import com.pearadmin.common.tools.SequenceUtil;
import com.pearadmin.common.web.domain.request.PageDomain;
import com.pearadmin.system.domain.SysRole;
import com.pearadmin.system.domain.SysUser;
import com.pearadmin.system.domain.SysUserRole;
import com.pearadmin.system.mapper.SysRoleMapper;
import com.pearadmin.system.mapper.SysUserMapper;
import com.pearadmin.system.mapper.SysUserRoleMapper;
import com.pearadmin.system.service.ISysUserService;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Describe: 用户服务实现类
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper,SysUser> implements ISysUserService {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private MailUtils mailUtils;

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
        return sysUserMapper.selectListPage(param);
    }

    /**
     * Describe: 根据条件查询用户列表数据  分页
     * Param: username
     * Return: 返回分页用户列表数据
     */
    @Override
    public IPage<SysUser> page(SysUser param, PageDomain pageDomain) {
        Page<SysUser> page = new Page<>(pageDomain.getPage(), pageDomain.getLimit());
        return sysUserMapper.selectListPage(page,param);
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
     * @param id
     */
    @Override
    public SysUser getById(Long id) {
        return sysUserMapper.selectByIdInfo(id);
    }

    /**
     * Describe: 根据 id 删除用户数据
     * Param: id
     * Return: Boolean
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean remove(Long id) {
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
    public boolean saveUserRole(Long userId, List<String> roleIds) {
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
        List<SysRole> allRole = sysRoleMapper.selectList(new SysRole());
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

    @Override
    public SysUser getUserByUsername(String username) {
        return sysUserMapper.selectByUsername(username);
    }

    @Override
    public SysUser getUserByEmail(String email) {
        return sysUserMapper.getUserByEmail(email);
    }

    @Override
    public int setFace(SysUser users) {
        int i = sysUserMapper.setFace(users);
        if (i > 0) {
            setUserInf0(users.getUserId());
        }
        return i;
    }

    @Override
    public int setUrls(String column, String url, Long userId) {
        if (!StrUtil.isBlank(column)) {
            String c ;
            switch (column) {
                case "wxUrl":
                    c = "wx_url";
                    break;
                case "qqUrl":
                    c = "qq_url";
                    break;
                case "githubUrl":
                    c = "github_url";
                    break;
                case "csdnUrl":
                    c = "csdn_url";
                    break;
                case "giteeUrl":
                    c = "gitee_url";
                    break;
                case "biliUrl":
                    c = "bili_url";
                    break;
                case "weiboUrl":
                    c = "weibo_url";
                    break;
                default:
                    return 0;
            }
            int i = sysUserMapper.setUrls(c, url, userId);
            if (i > 0) {
                setUserInf0(userId);
            }
            return i;
        }
        return 0;
    }

    @Override
    public int setsignature(String signature, Long userId) {
        int i = sysUserMapper.setsignature(signature, userId);
        if (i > 0) {
            setUserInf0(userId);
        }
        return i;
    }

    private SysUser setUserInf0(Long userId) {
        SysUser users1 = sysUserMapper.selectById(userId);
        redisUtil.set(Constants.RedisKey.USERINFO + userId,JSONUtil.toJsonStr(users1));
        return users1;
    }

    @Override
    public int unOrbdMail(Long userId, int bd) {
        int i = sysUserMapper.unOrbdMail(bd, userId);
        if (i > 0) {
            setUserInf0(userId);
        }
        return i;
    }

    @Override
    public int ghMail(String email, Long userId) {
        int i = sysUserMapper.ghMail(email, userId);
        if (i > 0) {
            setUserInf0(userId);
        }
        return i;
    }

    @Override
    public int setDeleteState(String ids,Integer deleteState) {
        if (StrUtil.isNotEmpty(ids)) {
            String[] split = ids.split(",");
            QueryWrapper<SysUser> queryWrapper = new QueryWrapper();
            // string数组转list<Integer>
            List<Integer> idList = Arrays.stream(split)
                    .map(s->Integer.parseInt(s.trim()))
                    .collect(Collectors.toList());
            queryWrapper.in("user_id",idList);
            SysUser users = new SysUser();
            users.setUserState(deleteState);
            return sysUserMapper.update(users,queryWrapper);
        }
        return 0;
    }

}
