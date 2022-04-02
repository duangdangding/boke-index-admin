package com.lsh.birthday;

import java.io.Serializable;
import java.sql.Timestamp;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserMsg implements Serializable {

    @TableId
    private Integer userId;
    @TableField("user_name")
    private String username;
    private String userIp;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp loginTime;
    private Integer loginSum;
    private String userAddress;
    private Integer userBg = 1;
    private String userXz;
}
