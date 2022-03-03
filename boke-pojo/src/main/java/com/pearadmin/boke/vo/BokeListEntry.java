package com.pearadmin.boke.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * bokes
 * 
 * @author bianj
 * @version 1.0.0 2021-03-31
 */
@Data
public class BokeListEntry implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -6860943679883810999L;

    /* This code was generated by TableGo tools, mark 1 begin. */
    @TableId
    private int bokeId;

    private String title;

    private String bokeCont;

    private String createTime;

    private Integer cateId;

    // 主要加这两行注解
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
//    private LocalDateTime updateTime;
    
    private String updateTime;

    private Integer userId;
    
    private String userName;

    private Integer likeNum;

    private Integer replyNum;

    private Integer lookNum;

    private String labelId;
    @TableField(exist = false)
    private String[] labelIds;
    
    private String cateName;
    
    private String introduction;
    
    private String createDate;
    
//    private String bokeCount;

    private Integer editorType;

    private Integer priPub;
    
    private String mdContent;
    
    private int topOrder;
    
//    签名
    private String signature;
    
    private Integer bokeZip;
    private Integer shareNum;
    private Integer bokeExamine;
    private Integer deleteState;
    private String labelNames;
    @TableField(exist = false)
    private String[] labNames;

    /* This code was generated by TableGo tools, mark 3 end. */
}