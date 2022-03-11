package com.pearadmin.boke.entry;

import java.sql.Timestamp;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * @Author lushao
 * @Description 相册类型
 * @Date 2021/6/9 10:28
 * @Version 1.0
 */
@Data
public class PhotoType {
    
    @TableId
    private Integer pTypeId;
    
    private String typeName;
    
    private String descript;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createTime;
    
    private String folder;
}
