package com.pearadmin.system.domain;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.type.Alias;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.baomidou.mybatisplus.annotation.TableId;
import com.pearadmin.common.web.base.BaseDomain;

import lombok.Getter;
import lombok.Setter;

/**
 * Describe: 用户领域模型
 * @author 就 眠 仪 式
 * @date 2019/10/23
 */
@Getter
@Setter
@Alias("SysUser")
public class SysUser extends BaseDomain implements UserDetails, CredentialsContainer {

    private static final long serialVersionUID = 1L;

    public SysUser() {
    }

    public SysUser(Long userId, String userName) {
        this.userId = userId;
        this.username = userName;
    }

    /**
     * 编号
     */
    @TableId
    private Long userId;

    /**
     * 账户
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐
     */
    private String salt;

    /**
     * 状态
     */
    private String status;

    /**
     * 姓名
     */
    private String realName;

    /**
     * 邮箱
     */

    private String email;

    private String loginIp;

    // private String userFace;

    private Timestamp registerTime;

    private Timestamp loginTime;
    private String loginType;

    private String registerIp;

    private Date birthday;

    private Integer userAge;

    private String signature;

    private Integer emailBind;

    private Integer phoneBind;

    private String wxUrl;
    private String qqUrl;
    private String csdnUrl;
    private String giteeUrl;
    private String githubUrl;
    private String weiboUrl;
    private String biliUrl;

    private int userState;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 性别
     */
    private String sex;

    /**
     * 电话
     */
    private String phone;

    /**
     * 所属部门
     */
    private String deptId;

    /**
     * 是否启用
     */
    private String enable;

    /**
     * 是否登录
     */
    private String login;

    /**
     * 计算列
     */
    private String roleIds;


    /**
     * 最后一次登录时间
     */
    private LocalDateTime lastTime;

    /**
     * 权限 这里暂时不用 security 的 Authorities
     */
    private List<SysPower> powerList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return "1".equals(this.getStatus()) ? true : false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return "1".equals(this.getEnable()) ? true : false;
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }
}
