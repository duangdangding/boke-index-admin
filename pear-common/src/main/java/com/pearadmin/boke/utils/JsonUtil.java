package com.pearadmin.boke.utils;

import cn.hutool.json.JSONUtil;
import com.pearadmin.boke.vo.EmojiEntry;
import com.pearadmin.boke.vo.ImgEntry;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author lushao
 * @Description json工具类
 * @Date 2021/4/23 20:00
 * @Version 1.0
 */
@Component
public class JsonUtil {
    
    public static void downFile(HttpServletResponse response,String json) {
        try {
            String fileName = "yuan.json";
            response.setContentType("application/octet-stream"); 
            response.setHeader("content-type", "application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
            IOUtils.copy(new ByteArrayInputStream(json.getBytes()), response.getOutputStream());
            response.flushBuffer();

            /*OutputStream os = new s
            ServletOutputStream out = response.getOutputStream();
            out.write(json.getBytes());
            out.flush();
            out.close();*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取流 获取json
     * @param file
     * @return
     */
    public static String readFile(MultipartFile file) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(file.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
       return null;
    }
    
    public String readFiles(String root) {
        File rootf = new File(root);
        File[] roots = rootf.listFiles();
        List<EmojiEntry> result = new ArrayList<>();
        for (int i = 0; i < (roots != null ? roots.length : 0); i++) {
            File file = roots[i];
            if (file.isFile()) {
                continue;
            }
            // System.out.println(file.getName());
            String title = file.getName();
            EmojiEntry emoji = new EmojiEntry();
            emoji.setType("image");
            emoji.setTitle(title);
            List<ImgEntry> content = new ArrayList<>();
            File[] imgs = file.listFiles();
            for (int j = 0; j < (imgs != null ? imgs.length : 0); j++) {
                File img = imgs[j];
                ImgEntry imge = new ImgEntry();
                imge.setAlt("");
                imge.setSrc("/" + rootf.getName() + "/" + title + "/" + img.getName());
                content.add(imge);
            }
            emoji.setContent(content);
            result.add((roots.length - 1 -i),emoji);
        }
        
        return JSONUtil.toJsonStr(result);
    }
    public static String readFiles(List<String> roots,String pre) {
        List<EmojiEntry> result = new ArrayList<>();
        for (int i = 0; i < roots.size(); i++) {
            File file = new File(roots.get(i));
            String title = file.getName();
            EmojiEntry emoji = new EmojiEntry();
            emoji.setType("image");
            emoji.setTitle(title);
            List<ImgEntry> content = new ArrayList<>();
            File[] imgs = file.listFiles();
            for (int j = 0; j < (imgs != null ? imgs.length : 0); j++) {
                File img = imgs[j];
                ImgEntry imge = new ImgEntry();
                imge.setAlt("");
                imge.setSrc(pre + title + "/" + img.getName());
                content.add(imge);
            }
            emoji.setContent(content);
            result.add(emoji);
        }
        
        return JSONUtil.toJsonStr(result);
    }
    
    public static String createArrJson(List<String> roots,String pre) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < roots.size(); i++) {
            File file = new File(roots.get(i));
            String title = file.getName();
            File[] imgs = file.listFiles();
            for (int j = 0; j < (imgs != null ? imgs.length : 0); j++) {
                File img = imgs[j];
                result.add(pre + title + "/" + img.getName());
            }
        }
        
        return JSONUtil.toJsonStr(result);
    }

    public static void main(String[] args) {
        List<String> roots = new ArrayList<>();
//        roots.add("E:\\workspaces\\idea2020\\myboke\\src\\main\\resources\\static\\emoji\\xhj2");
//        roots.add("E:\\workspaces\\idea2020\\myboke\\src\\main\\resources\\static\\emoji\\xhj1");
//        roots.add("E:\\workspaces\\idea2020\\myboke\\src\\main\\resources\\static\\emoji\\qq");
//        roots.add("E:\\workspaces\\idea2020\\myboke\\src\\main\\resources\\static\\emoji\\other");
        /*String locationPath = ClassUtils.getDefaultClassLoader().getResource("static").getPath();
        System.out.println(locationPath);
        try {
            String locationPath2 = ResourceUtils.getURL("classpath:").getPath();
            System.out.println(locationPath2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/
        String resource = JsonUtil.class.getResource("/static/editor/wangeditor/emoji/").getPath().substring(1);
        roots.add(resource + "haa");
        // roots.add(resource + "xhj2");
        // roots.add(resource + "xhj1");
        // roots.add(resource + "qq");
        // roots.add(resource + "other");
        // roots.add(resource + "fenlan");
//        System.out.println(resource);
//        String s = readFiles(roots, "/emoji/");
//        System.out.println(s);
        String s = readFiles(roots,"/editor/wangeditor/emoji/");
        System.out.println(s);
    }
}
