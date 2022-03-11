package com.pearadmin.boke.entry;

import java.sql.Timestamp;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * @Author lushao
 * @Description 文件上传记录
 * @Date 2021/6/2 17:22
 * @Version 1.0
 */
@Data
public class FileRecode {
    @TableId
    private Long fileId;
    private String fileUrl;
    private String fileName;
    private String fileType;
    private Long userId;
//    1、ftp，2txy，3阿里，4七牛
    private Integer uploadTypeId;
//    文件所属位置1、富文本2、markdown3、头像4、相册5、其他
    private Integer fileOwnId;
    private String localPath;
    private long fileSize;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createTime;
    private String oosKey;
}
