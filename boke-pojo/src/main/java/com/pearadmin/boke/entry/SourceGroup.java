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
* 视频源分组(source_group)
*
* @author lushao
* @version 1.0.0 2021-07-05 20:14:11
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "视频源分组")
public class SourceGroup {
    @TableId
    @ApiModelProperty(value = "group_id")
    private Integer groupId;
        
    @ApiModelProperty(value = "group_name")
    private String groupName;
        
    @ApiModelProperty(value = "group_desc")
    private String groupDesc;
        
    @ApiModelProperty(value = "create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
        
}