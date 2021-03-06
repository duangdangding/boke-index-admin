package com.pearadmin.boke.entry;

import java.io.Serializable;
import java.sql.Timestamp;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class Photos implements Serializable {
    
//    @TableId(value = "photo_id",type = IdType.AUTO)
    @TableId
    private Integer photoId;
    
    private String photoTitle;
    
    private Integer photoType;
    
    private String photoUrl;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createTime;
    
    private Long userId;
    
    private String oosKey;
    
    // 0删除 1不删除
    private Integer deleteState;
    
    private String oldRatio;
    
    private String newRatio;
    
    private Integer oldWidth;
    private Integer newWidth;
    private Integer oldHeight;
    private Integer newHeight;
    
//    缩略图
    private String thumbUrl;
//    缩略图oss
    private String thumbOss;
    

}
