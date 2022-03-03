package com.pearadmin.boke.entry;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @Author lushao
 * @Description 相册类型
 * @Date 2021/6/9 10:28
 * @Version 1.0
 */
@Data
public class PhotoType {
    
    @TableId
    private Integer pTypeId;
    
    private String typeName;
    
    private String descript;
    
    private Timestamp createTime;
    
    private String folder;
}
