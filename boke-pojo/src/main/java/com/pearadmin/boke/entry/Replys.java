package com.pearadmin.boke.entry;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;

@Data
public class Replys implements Serializable {
    
    @TableId(value = "reply_id",type = IdType.AUTO)
    private Integer replyId;
    
    private Long userId;
    
    private Integer bokeId;
    
    private Integer commentId;

//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String replyTime;
    
    private String replyContent;
    
    private String replyIp;
    
    @TableField(exist = false)
    private String userName;
    
    @TableField(exist = false)
    private String avatar;

    //    0未审核1审核不通过
    private Integer replyVerify;
}
