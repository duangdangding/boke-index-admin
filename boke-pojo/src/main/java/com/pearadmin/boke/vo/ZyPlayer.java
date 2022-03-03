package com.pearadmin.boke.vo;

import lombok.Data;

/**
 * @Author lushao
 * @Description ZyPlayer导入数据格式
 * @Date 2021/7/5 19:08
 * @Version 1.0
 */
@Data
public class ZyPlayer {
    
    private Integer id;
    
    private String key;
    
    private String api;
    
    private String name;
    
    private String download;
    
    private String jiexiUrl;
    
    private String group;
    
    private boolean isActive = true;
}
