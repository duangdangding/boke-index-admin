package com.pearadmin.boke.entry;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
* 留言墙(message_wall)
*
* @author lushao
* @version 1.0.0 2022-03-23 14:28:07
*/
@Data
@ApiModel(description = "留言墙")
@TableName("message_wall")
public class MessageWall {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "~wall_id")
    private Integer wallId;
        
    @TableField("nick_name")
    @ApiModelProperty(value = "~nick_name")
    private String nickName;
        
    @TableField("content")
    @ApiModelProperty(value = "~content")
    private String content;
        
    @TableField("create_time")
    @ApiModelProperty(value = "~create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
        
    @TableField("back_color")
    @ApiModelProperty(value = "背景颜色~back_color")
    private String backColor;
        
    @TableField("back_img")
    @ApiModelProperty(value = "~back_img")
    private String backImg;
        
    @TableField("rotate")
    @ApiModelProperty(value = "旋转的角度~rotate")
    private Integer rotate;
        
    @TableField("xy_point")
    @ApiModelProperty(value = "坐标点~xy_point")
    private String xyPoint;
    
    @TableField("delete_state")
    private Integer deleteState;
        
}