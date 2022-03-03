package com.pearadmin.boke.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author lushao
 * @Description 图片
 * @Date 2021/4/23 20:15
 * @Version 1.0
 */
@Data
public class ImgEntry implements Serializable {
    
    private String alt;
    
    private String src;
}
