package com.pearadmin.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pearadmin.system.domain.SysConfig;

/**
 * Describe: 系统配置接口
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 */
// @Mapper
public interface SysConfigMapper {

    /**
     * Describe: 查询系统配置信息
     * Param: SysConfig
     * Return: 执行结果
     */
    IPage<SysConfig> selectList(Page<SysConfig> page , @Param("vo") SysConfig param);
    List<SysConfig> selectList( @Param("vo")SysConfig param);

    /**
     * Describe: 添加系统配置
     * Param: SysConfig
     * Return: 执行结果
     */
    Integer insert(SysConfig sysConfig);

    /**
     * Describe: 根据 Id 查询系统配置
     * Param: id
     * Return: SysConfig
     */
    SysConfig selectById(@Param("id") String id);

    /**
     * Describe: 根据 Code 查询系统配置
     * Param: code
     * Return: SysConfig
     */
    SysConfig selectByCode(@Param("code") String code);

    /**
     * Describe: 根据 Id 修改系统配置
     * Param: SysConfig
     * Return: Boolean
     */
    Integer updateById(SysConfig sysConfig);

    /**
     * Describe: 根据 Id 删除系统配置
     * Param: id
     * Return: SysConfig
     */
    Integer deleteById(String id);

    /**
     * Describe: 根据 Id 删除系统配置
     * Param: id
     * Return: SysConfig
     */
    Integer deleteByIds(String[] id);

}
