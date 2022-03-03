package com.pearadmin.boke.utils;

import cn.hutool.core.img.ImgUtil;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.*;

public class MyImgUtil {

    /**
     * 按比例压缩图片，并转换成流
     * @param imageFile
     * @param scale
     * @param imageType
     * @return
     * @throws IOException
     */
    public static InputStream scalImgToInput(MultipartFile imageFile,Float scale,String imageType) {
        scale = null == scale ? 0.5f : scale;
//        缩略图
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImgUtil.scale(imageFile.getInputStream(),baos,scale);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        转inputstream
        return new ByteArrayInputStream(baos.toByteArray());
    }

    /**
     * MultipartFile 转 BufferedImage
     * @param file
     * @return
     */
    public static BufferedImage convertBufferedImage(MultipartFile file) {
        BufferedImage srcImage = null;
        try {
            FileInputStream in = (FileInputStream) file.getInputStream();
            srcImage = javax.imageio.ImageIO.read(in);
        } catch (IOException e) {
            System.out.println("读取图片文件出错！" + e.getMessage());
        }
        return srcImage;
    }
}
