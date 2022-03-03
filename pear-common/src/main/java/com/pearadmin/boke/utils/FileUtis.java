package com.pearadmin.boke.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author lushao
 * @Description 文件工具类
 * @Date 2021/9/30 19:36
 * @Version 1.0
 */
public class FileUtis {
    
    public static String readFileToJsonStr(String path) {
        List<String> strings = FileUtil.readUtf8Lines(path);
        List<Map<String,String>> maps = new ArrayList<>();
        for (String string : strings) {
            String[] split = string.split(",");
            String s0 = split[0];
            String s1 = split[1];
            Map<String,String> map = new HashMap<>();
            map.put("name",s0);
            map.put("api",s1);
            maps.add(map);
        }
        String s = JSONUtil.toJsonStr(maps);
        return s;
    }
    public static void readFileToJsonStr2(String path) {
        List<String> strings = FileUtil.readUtf8Lines(path);
        StringBuffer sb = new StringBuffer();
        sb.append("(");
        for (String string : strings) {
            sb.append("'").append(string).append("',");
        }
        sb.append(")");
        System.out.println(sb.toString());
    }

    public static void main(String[] args) {
//        String path = "E:\\test\\jx.txt";
//         String path = "C:\\Users\\lushao\\Desktop\\jx.txt";
        String path = "C:\\Users\\Administrator\\Desktop\\jiexi.txt";
//        String s = readFileToJsonStr(path);
        readFileToJsonStr2(path);
//        System.out.println(s);
    }
}
