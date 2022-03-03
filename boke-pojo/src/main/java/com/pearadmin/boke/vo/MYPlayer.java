package com.pearadmin.boke.vo;

import lombok.Data;

/**
 * 猫影影视数据结构
 */
@Data
public class MYPlayer {
    
    private String key;
    
    private String name;
    
    private Integer type = 0;
    
    private String api;
    
    private String playUrl = "";
    
    private String[] categories;
    
}
