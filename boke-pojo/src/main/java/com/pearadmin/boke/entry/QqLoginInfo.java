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
* qq授权的记录(qq_login_info)
*
* @author lushao
* @version 1.0.0 2021-07-25 12:07:48
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "qq授权的记录")
public class QqLoginInfo {
    @TableId
    @ApiModelProperty(value = "info_id")
    private Integer infoId;
        
    @ApiModelProperty(value = "qq_num")
    private String qqNum;
        
    @ApiModelProperty(value = "qq_info")
    private String qqInfo;
        
    @ApiModelProperty(value = "create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
        
    @ApiModelProperty(value = "update_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
        
    @ApiModelProperty(value = "ip_address")
    private String ipAddress;
        
    @ApiModelProperty(value = "qq_callback")
    private String qqCallback;
    
    @ApiModelProperty(value = "user_id")
    private Integer userId;
        
}