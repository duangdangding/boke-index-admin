package com.pearadmin.boke.entry;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
* 顶部导航栏(navigation)
*
* @author lushao
* @version 1.0.0 2021-06-16 11:35:05
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "顶部导航栏")
public class Navigation {
    @TableId
    @ApiModelProperty(value = "nav_id")
    private Integer navId;
        
    @ApiModelProperty(value = "nav_title")
    private String navTitle;
        
    @ApiModelProperty(value = "nav_url")
    private String navUrl;
        
    @ApiModelProperty(value = "create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
        
    @ApiModelProperty(value = "排序")
    private Integer orderNum;
        
    @ApiModelProperty(value = "0在本页面1新窗口")
    private Boolean target;
        
    @ApiModelProperty(value = "parent_id")
    private Integer parentId;
        
    @ApiModelProperty(value = "id_str")
    private String idStr;
        
    @ApiModelProperty(value = "html_str")
    private String htmlStr;
        
    @ApiModelProperty(value = "0显示1不显示")
    private Boolean navShow;
        
    @ApiModelProperty(value = "登陆验证 0不登陆显示1登陆显示3都显示4管理员才显示")
    private Integer authorization;
        
}