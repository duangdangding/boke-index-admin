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
* 解析地址(parse_urls)
*
* @author lushao
* @version 1.0.0 2021-07-03 11:13:41
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "解析地址")
public class ParseUrls {
    @TableId
    @ApiModelProperty(value = "~parse_id")
    private Integer parseId;
        
    @TableField("parse_name")
    @ApiModelProperty(value = "~parse_name")
    private String parseName;
        
    @TableField("parse_url")
    @ApiModelProperty(value = "~parse_url")
    private String parseUrl;
        
    @TableField("create_time")
    @ApiModelProperty(value = "~create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
        
    @TableField("parse_active")
    @ApiModelProperty(value = "是否可用~parse_active")
    private Integer parseActive;
        
    @TableField("parse_order")
    @ApiModelProperty(value = "优先级~parse_order")
    private Integer parseOrder;
    private Integer shoudong;
        
}