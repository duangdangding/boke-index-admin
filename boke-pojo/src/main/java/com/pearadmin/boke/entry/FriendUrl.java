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
* friend_url(friend_url)
*
* @author lushao
* @version 1.0.0 2021-06-16 10:06:29
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "friend_url")
public class FriendUrl {
    @TableId
    @ApiModelProperty(value = "")
    private Integer friendId;
        
    @ApiModelProperty(value = "url")
    private String url;
        
    @ApiModelProperty(value = "name")
    private String name;
        
    @ApiModelProperty(value = "img_url")
    private String imgUrl;
        
    @ApiModelProperty(value = "descript")
    private String descript;
        
    @ApiModelProperty(value = "")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
        
    @ApiModelProperty(value = "")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;
        
    @ApiModelProperty(value = "sort_num")
    private Integer sortNum;
        
    @ApiModelProperty(value = "url_show")
    private Boolean urlShow;
        
}