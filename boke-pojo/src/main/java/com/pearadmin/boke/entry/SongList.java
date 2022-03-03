package com.pearadmin.boke.entry;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
* song_list(song_list)
*
* @author lushao
* @version 1.0.0 2021-06-16 09:43:56
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "song_list")
public class SongList {
    @TableId
    @ApiModelProperty(value = "")
    private Integer id;
        
    @ApiModelProperty(value = "song_no")
    private String songNo;
        
    @ApiModelProperty(value = "song_server")
    private String songServer;
        
    @ApiModelProperty(value = "song_type")
    private String songType;
        
    @ApiModelProperty(value = "song_theme")
    private String songTheme;
        
    @ApiModelProperty(value = "")
    private Boolean aotoPaly;
    
    @ApiModelProperty(value = "")
    private Boolean leftFixed;
    
    @ApiModelProperty(value = "userId")
    private Integer userId;
        
}