package com.pearadmin.boke.entry;

import java.io.Serializable;
import java.sql.Timestamp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;

@Data
public class BokeTop implements Serializable {
    
    @TableId(value = "top_id",type = IdType.AUTO)
    private Integer topId;
    
    private Long userId;
    
    private Integer bokeId;
    
    @TableField(exist = false)
    private Integer topOrder;
    
    @TableField(exist = false)
    private Timestamp topTime;
}
