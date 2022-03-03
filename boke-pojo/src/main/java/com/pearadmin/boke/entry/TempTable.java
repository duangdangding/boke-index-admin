package com.pearadmin.boke.entry;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
* 一些中转数据(temp_table)
*
* @author lushao
* @version 1.0.0 2021-07-27 15:31:04
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "一些中转数据")
public class TempTable {
    @TableId
    @ApiModelProperty(value = "~temp_id")
    private Integer tempId;
        
    @TableField("temp_data")
    @ApiModelProperty(value = "~temp_data")
    private String tempData;
        
}