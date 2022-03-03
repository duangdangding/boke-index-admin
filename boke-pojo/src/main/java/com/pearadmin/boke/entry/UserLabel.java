package com.pearadmin.boke.entry;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
* 用户和标签表关联表(user_label)
*
* @author lushao
* @version 1.0.0 2021-08-11 10:16:37
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "用户和标签表关联表")
public class UserLabel {
    @TableId
    @ApiModelProperty(value = "~ub_id")
    private Integer ubId;
        
    @TableField("user_id")
    @ApiModelProperty(value = "~user_id")
    private Integer userId;
        
    @TableField("label_id")
    @ApiModelProperty(value = "~label_id")
    private Integer labelId;
        
    @TableField("order_num")
    @ApiModelProperty(value = "~order_num")
    private Integer orderNum;
        
    @TableField("create_time")
    @ApiModelProperty(value = "~create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
        
}