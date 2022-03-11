package com.pearadmin.boke.entry;

import java.io.Serializable;
import java.sql.Timestamp;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class Visits implements Serializable {
    
    @TableId
    private Integer visitId;
    
    private String visitIp;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp visitTime;
    
    private long visitCount;
    
    private Long userId;
    
    @TableField(exist = false)
    private String username;
    
    private String visitClient;
    
    private String country;
    
    private String userAgent;
    
    private String ipAddr;
    private String city;
}
