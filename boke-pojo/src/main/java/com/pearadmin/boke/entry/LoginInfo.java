package com.pearadmin.boke.entry;

import lombok.Data;

import java.util.Date;

/**
 * @Author lushao
 * @Description 简单封装用户登录信息
 * @Date 2021/7/25 12:50
 * @Version 1.0
 */
@Data
public class LoginInfo {

    private Integer userId;

    private String userName;

    private String userFace;

    private Date loginTime;

    private String signature;

    private String wxUrl;
    private String qqUrl;
    private String csdnUrl;
    private String giteeUrl;
    private String githubUrl;
    private String weiboUrl;
    private String biliUrl;
}
