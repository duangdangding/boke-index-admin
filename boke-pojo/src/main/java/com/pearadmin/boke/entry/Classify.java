package com.pearadmin.boke.entry;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
* 导航分类(classify)
*
* @author lushao
* @version 1.0.0 2022-01-17 13:54:52
*/
@Data
@ApiModel(description = "导航分类")
@TableName("classify")
public class Classify {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "~id")
    private Integer id;
        
    @ApiModelProperty(value = "~title")
    private String title;
        
    @ApiModelProperty(value = "~sort")
    private Integer sort;
    
    @ApiModelProperty(value = "~delete_state")
    private Integer deleteState;
        
    @ApiModelProperty(value = "~create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    
    @TableField(exist = false)
    private List<FrantPage> frantPages = new ArrayList<>();
        
}