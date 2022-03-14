package com.pearadmin.boke.utils.contains;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 书籍分类
 * @author: lzz
 * @create: 2021-09-01 08:53
 **/

public enum BookType {

    WD(1, "boke/wd"),
    MD(2, "boke/md"),

    ;
    private Integer type;
    private String value;

    BookType(Integer type, String value) {
        this.type = type;
        this.value = value;
    }

    public static Map<Integer, String> typeMap = new HashMap<>();

    static {
        BookType[] values = BookType.values();
        for (BookType value : values) {
            typeMap.put(value.type, value.value);
        }
    }
}
