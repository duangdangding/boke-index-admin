package com.pearadmin.system.domain;

import org.apache.ibatis.type.Alias;

import lombok.Data;

/**
 * Describe: 用户角色映射关系
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 */

@Data
@Alias("SysUserRole")
public class SysUserRole {

    /**
     * 映射标识
     */
    private String id;

    /**
     * 用户编号
     */
    private Long userId;

    /**
     * 角色编号
     */
    private String roleId;

}
