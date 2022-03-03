package com.pearadmin.boke.entry;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
* 用户自定义数据(user_custom)
*
* @author lushao
* @version 1.0.0 2021-06-16 11:35:59
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "用户自定义数据")
public class UserCustom {
    @TableId
    @ApiModelProperty(value = "id")
    private Integer id;
        
    @ApiModelProperty(value = "user_id")
    private Integer userId;
        
    @ApiModelProperty(value = "赞赏图标内容")
    private String gratuity;
        
    @ApiModelProperty(value = "0显示1不显示赞赏")
    private Integer gratuityIs;
        
    @ApiModelProperty(value = "文章页面标题前的图标是否显示1不显示")
    private Integer cuteiconIs;
        
    @ApiModelProperty(value = "文章页面标题前的图标")
    private String cuteicon;
    
}