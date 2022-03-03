package com.pearadmin.boke.entry;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.sql.Timestamp;

public class BokeTop implements Serializable {
    
    @TableId(value = "top_id",type = IdType.AUTO)
    private Integer topId;
    
    private Integer userId;
    
    private Integer bokeId;
    
    @TableField(exist = false)
    private Integer topOrder;
    
    @TableField(exist = false)
    private Timestamp topTime;

    public Integer getTopId() {
        return topId;
    }

    public void setTopId(Integer topId) {
        this.topId = topId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBokeId() {
        return bokeId;
    }

    public void setBokeId(Integer bokeId) {
        this.bokeId = bokeId;
    }

    public Integer getTopOrder() {
        return topOrder;
    }

    public void setTopOrder(Integer topOrder) {
        this.topOrder = topOrder;
    }

    public Timestamp getTopTime() {
        return topTime;
    }

    public void setTopTime(Timestamp topTime) {
        this.topTime = topTime;
    }
}
