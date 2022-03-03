package com.pearadmin.boke.entry;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
* zyplayer视频源(video_source)
*
* @author lushao
* @version 1.0.0 2021-06-30 19:58:00
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "zyplayer视频源")
public class VideoSource {
    @TableId
    @ApiModelProperty(value = "source_id")
    private Integer sourceId;
        
    @ApiModelProperty(value = "暂时不知道什么作用")
    private String sourceKey;
        
    @ApiModelProperty(value = "视频源名字")
    private String sourceName;
        
    @ApiModelProperty(value = "视频源地址，解析地址")
    private String sourceApi;
        
    @ApiModelProperty(value = "全局搜索是否使用这个api")
    private Boolean useInSearchAll;
        
    @ApiModelProperty(value = "1正常2不正常")
    private Integer sourceType;
        
    @ApiModelProperty(value = "是否可以使用")
    private Boolean sourceSee;

    @ApiModelProperty(value = "group_id")
    private Integer groupId;

    @ApiModelProperty(value = "parse_id")
    private Integer parseId;
    //web中可用0可用1不可用
    private Integer webAvailable;
    // 0否1是
    private Integer needParse;
    //手动检测0已检测1未检测
    private Integer shoudong;
    private String createTime;
    
    @TableField(exist = false)
    private String groupName;
    @TableField(exist = false)
    private String parseApi;
    
    private String category;
        
}