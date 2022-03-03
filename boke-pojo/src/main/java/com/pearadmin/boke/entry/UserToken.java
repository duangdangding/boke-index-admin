package com.pearadmin.boke.entry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Administrator
 * @Description 登录之后返回的实体
 * @Date 2021/4/9 11:28
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserToken {
    
    private LoginInfo users;
    
    private String token;

}
