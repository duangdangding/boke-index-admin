package com.pearadmin.boke.entry;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class Photos implements Serializable {
    
//    @TableId(value = "photo_id",type = IdType.AUTO)
    @TableId
    private Integer photoId;
    
    private String photoTitle;
    
    private Integer photoType;
    
    private String photoUrl;
    
    private Timestamp createTime;
    
    private Integer userId;
    
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
