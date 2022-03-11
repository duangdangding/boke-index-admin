package com.pearadmin.boke.entry;

import java.sql.Timestamp;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * categorys
 * 
 * @author bianj
 * @version 1.0.0 2021-03-31
 */
@Data
public class Categorys implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 3030603292572745467L;

    @TableId
    private Integer cateId;

    private String cateName;

    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createTime;

    private Integer parentCateId;
    
    private Integer deleteState;

}