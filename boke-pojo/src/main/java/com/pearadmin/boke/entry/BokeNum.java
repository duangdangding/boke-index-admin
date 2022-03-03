package com.pearadmin.boke.entry;

import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;

@Data
public class BokeNum {
    
    @TableId
    private Integer bokeId;

    private Integer likeNum;

    private Integer replyNum;

    private Integer lookNum;

    private Integer shareNum;
    
}
