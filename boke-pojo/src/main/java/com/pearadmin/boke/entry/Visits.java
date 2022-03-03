package com.pearadmin.boke.entry;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class Visits implements Serializable {
    
    @TableId
    private Integer visitId;
    
    private String visitIp;
    
    private Timestamp visitTime;
    
    private long visitCount;
    
    private Integer userId;
    
    @TableField(exist = false)
    private String userName;
    
    private String visitClient;
    
    private String country;
    
    private String userAgent;
    
    private String ipAddr;
    private String city;
}
