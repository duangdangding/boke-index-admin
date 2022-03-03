package com.pearadmin.system.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pearadmin.common.web.domain.request.PageDomain;
import com.pearadmin.system.domain.SysDept;

/**
 * Describe: 部门服务接口类
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 */
public interface ISysDeptService {

    /**
     * Describe: 查询部门数据
     * Param: queryRoleParam
     * Return: 操作结果
     */
    List<SysDept> list(SysDept param);

    /**
     * Describe: 分页查询部门数据
     * Param: queryRoleParam
     * Param: pageDomain
     * Return: 操作结果
     */
    // PageInfo<SysDept> page(SysDept param, PageDomain pageDomain);
    IPage<SysDept> page(SysDept param, PageDomain pageDomain);

    /**
     * Describe: 保存部门数据
     * Param: SysDept
     * Return: 操作结果
     */
    boolean save(SysDept sysDept);

    /**
     * Describe: 根据 id 获取部门信息
     * Param: id
     * Return: 操作结果
     */
    SysDept getById(String id);

    /**
     * Describe: 根据 id 修改用户数据
     * Param: ids
     * Return: 操作结果
     */
    boolean update(SysDept sysDept);

    /**
     * Describe: 根据 id 删除部门数据
     * Param: id
     * Return: 操作结果
     */
    Boolean remove(String id);

    /**
     * Describe: 根据 id 删除部门数据
     * Param: ids
     * Return: 操作结果
     */
    boolean batchRemove(String[] ids);

    /**
     * Describe: 根据 parentId 查询部门数据
     * Param: parentId
     * Return: 操作结果
     */
    List<SysDept> selectByParentId(String tenantId);
}
