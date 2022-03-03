package com.pearadmin.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pearadmin.system.domain.SysDept;

/**
 * Describe: 部门接口
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 */
// @Mapper
public interface SysDeptMapper {

    /**
     * Describe: 查询部门列表
     * Param: SysDept
     * Return: List<SysDept>
     */
    List<SysDept> selectList( @Param("vo")SysDept param);
    IPage<SysDept> selectList(Page<SysDept> page, @Param("vo")SysDept param);

    /**
     * Describe: 添加部门数据
     * Param: SysDept
     * Return: 执行结果
     */
    Integer insert(SysDept sysDept);

    /**
     * Describe: 根据 Id 查询部门
     * Param: id
     * Return: SysDept
     */
    SysDept selectById(@Param("id") String id);

    /**
     * Describe: 根据 Id 修改部门
     * Param: SysDept
     * Return: Integer
     */
    Integer updateById(SysDept sysDept);

    /**
     * Describe: 根据 Id 删除部门
     * Param: id
     * Return: Integer
     */
    Integer deleteById(String id);

    /**
     * Describe: 根据 Id 批量删除
     * Param: ids
     * Return: Integer
     */
    Integer deleteByIds(String[] ids);

    /**
     * Describe: 根据 parentId 查询部门
     * Param: parentId
     * Return: SysDept
     */
    List<SysDept> selectListByParentId(String parentId);

}