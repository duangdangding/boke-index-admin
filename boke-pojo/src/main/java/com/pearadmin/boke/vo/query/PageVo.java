package com.pearadmin.boke.vo.query;

import com.baomidou.mybatisplus.annotation.TableField;

import lombok.Data;

@Data
public class PageVo {

    @TableField(exist = false)
    Integer page;
    @TableField(exist = false)
    Integer limit;
}
