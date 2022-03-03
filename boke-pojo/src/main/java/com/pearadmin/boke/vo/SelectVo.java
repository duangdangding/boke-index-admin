package com.pearadmin.boke.vo;

import lombok.Data;

/**
 * @Author lushao
 * @Description select元素包装类
 * @Date 2021/6/9 11:02
 * @Version 1.0
 */
@Data
public class SelectVo {
    private Object key;
    private Object value;
    
    public SelectVo(Object key,Object value) {
        this.key =key;
        this.value = value;
    }
}
