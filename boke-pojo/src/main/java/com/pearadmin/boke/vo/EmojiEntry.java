package com.pearadmin.boke.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author lushao
 * @Description 表情包实体类
 * @Date 2021/4/23 20:12
 * @Version 1.0
 */
@Data
public class EmojiEntry implements Serializable {
    
    private String title;
    
    private String type;
    
    private List<ImgEntry> content;
}
