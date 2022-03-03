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
* 后台管理菜单(menu)
*
* @author lushao
* @version 1.0.0 2022-01-18 08:18:23
*/
@Data
@ApiModel(description = "后台管理菜单")
@TableName("menu")
public class Menu {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "~menu_id")
    private Integer menuId;
        
    @ApiModelProperty(value = "~menu_parent_id")
    private Integer menuParentId;
        
    @ApiModelProperty(value = "~menu_name")
    private String menuName;
    
    @ApiModelProperty(value = "~menu_url")
    private String menuUrl;
        
    @ApiModelProperty(value = "~menu_icon")
    private String menuIcon;
        
    @ApiModelProperty(value = "~sort")
    private Integer sort;
        
    @ApiModelProperty(value = "~create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
        
    @ApiModelProperty(value = "~delete_state")
    private Integer deleteState;
    
    @TableField(exist = false)
    private String menuParentName;
        
    @TableField(exist = false)
    private List<Menu> menus = new ArrayList<>();
        
}