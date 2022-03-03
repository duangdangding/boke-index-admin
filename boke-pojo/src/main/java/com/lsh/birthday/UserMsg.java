package com.lsh.birthday;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
public class UserMsg implements Serializable {

    @TableId
    private Integer userId;
    private String userName;
    private String userIp;
    private Timestamp loginTime;
    private Integer loginSum;
    private String userAddress;
    private Integer userBg = 1;
    private String userXz;
}
