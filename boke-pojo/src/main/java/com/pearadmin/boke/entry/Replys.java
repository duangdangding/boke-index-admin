package com.pearadmin.boke.entry;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class Replys implements Serializable {
    
    @TableId(value = "reply_id",type = IdType.AUTO)
    private Integer replyId;
    
    private Integer userId;
    
    private Integer bokeId;
    
    private Integer commentId;

//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String replyTime;
    
    private String replyContent;
    
    private String replyIp;
    
    @TableField(exist = false)
    private String userName;
    
    @TableField(exist = false)
    private String userFace;

    //    0未审核1审核不通过
    private Integer replyVerify;
}
