package com.pearadmin.boke.entry;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pearadmin.boke.vo.query.PageVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
* 首页导航(frant_page)
*
* @author lushao
* @version 1.0.0 2022-01-17 13:54:52
*/
@Data
@ApiModel(description = "首页导航")
@TableName("frant_page")
public class FrantPage extends PageVo {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "~frant_id")
    private Integer frantId;
        
    @ApiModelProperty(value = "标题~frant_title")
    private String frantTitle;
        
    @ApiModelProperty(value = "描述~frant_desc")
    private String frantDesc;
        
    @ApiModelProperty(value = "~create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
        
    @ApiModelProperty(value = "0不可用 1可用~delete_state")
    private Integer deleteState;
        
    @ApiModelProperty(value = "排序asc~frant_sort")
    private Integer frantSort;
        
    @ApiModelProperty(value = "~classify_id")
    private Integer classifyId;
        
    @ApiModelProperty(value = "~frant_url")
    private String frantUrl;
        
    @ApiModelProperty(value = "~frant_icon")
    private String frantIcon;
    
    @TableField(exist = false)
    private String classifyName;
        
}