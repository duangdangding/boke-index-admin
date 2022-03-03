package com.pearadmin.boke.vo;

import lombok.Data;

/**
 * 视频源VO
 */
@Data
public class SourceVo {
    
    private Integer id;
    private String key;
    
    private String name;
    
    private String api;
    
    private Boolean useInSearchAll;
}
