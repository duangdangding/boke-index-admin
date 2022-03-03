package com.pearadmin.boke.service.impl;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lsh.mapper.boke.PhotoTypeMapper;
import com.lsh.mapper.boke.PhotosMapper;
import com.pearadmin.boke.entry.PhotoType;
import com.pearadmin.boke.entry.Photos;
import com.pearadmin.boke.service.FileRecodeService;
import com.pearadmin.boke.service.UploadFileService;
import com.pearadmin.boke.utils.MyImgUtil;
import com.pearadmin.boke.utils.MyStringUtil;
import com.pearadmin.boke.utils.RegExpUtil;
import com.pearadmin.boke.utils.TokenUtil;
import com.pearadmin.boke.utils.contains.Constants;
import com.pearadmin.boke.utils.exception.BizException;
import com.pearadmin.boke.utils.upload.QnyUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;

import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UploadFileServiceImpl implements UploadFileService {

    @Autowired
    private QnyUtil qnyUtil;
    @Autowired
    private FileRecodeService recodeService;
    @Autowired
    private PhotosMapper photosMapper;
    @Autowired
    private PhotoTypeMapper photoTypeMapper;

    @Override
    @Transactional
    public Map<String, Object> uploadPhotoReturnPath(MultipartFile image, Integer photoType) {
        PhotoType byId = photoTypeMapper.selectById(photoType);
        String floder = byId.getFolder() + "/";
//      上传原图
        Map<String,String> s = qnyUtil.upload2Qiniu(image, Constants.UploadDir.PHOTOS + floder);
        recodeService.saveUploadRecode(image,s,Constants.UploadDir.OWN_PH,Constants.UploadDir.OSSTYPE_QN);
        String key = s.get(Constants.UploadDir.OOSKEY);
        String[] keys = key.split("\\.");
        String imageType = "." + keys[1];
        String uploadKey = keys[0] + "_thumb" + imageType;
//        按比例缩放图片
        InputStream inputStream = MyImgUtil.scalImgToInput(image, 0.3f, imageType);
//        上传缩略图
        Map<String, String> thumbMap = qnyUtil.upload2QiniuStream(inputStream, uploadKey);
        recodeService.saveUploadRecode(inputStream,key,thumbMap,Constants.UploadDir.OWN_PH,Constants.UploadDir.OSSTYPE_QN);
        String path = s.get(Constants.UploadDir.FILLPATH);
        Photos photos = new Photos();
        photos.setPhotoType(photoType);
        int width;
        int height;
        InputStream in = null;
        try {
            in = image.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 图片格式是webp的
        if (image.getContentType().contains("webp")) {
            byte[] bytes = new byte[30];
            try {
                in.read(bytes, 0, bytes.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
            width = ((int) bytes[27] & 0xff) << 8
                    | ((int) bytes[26] & 0xff);
            height = ((int) bytes[29] & 0xff) << 8
                    | ((int) bytes[28] & 0xff);
        } else {
            BufferedImage sourceImg = null;
            try {
                sourceImg = ImageIO.read(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
            width = sourceImg.getWidth();
            height = sourceImg.getHeight();
        }
        String fs = MyStringUtil.getFs(width, height);
        photos.setOldRatio(fs);
        photos.setOldWidth(width);
        photos.setOldHeight(height);
        if (width > height) {
            photos.setNewWidth(194);
            photos.setNewHeight(120);
        } else if (width < height){
            photos.setNewWidth(130);
            photos.setNewHeight(200);
        } else {
            photos.setNewWidth(120);
            photos.setNewHeight(120);
        }
        photos.setUserId(TokenUtil.USERID);
        photos.setPhotoTitle(byId.getTypeName());
        photos.setPhotoUrl(path);
        photos.setOosKey(s.get(Constants.UploadDir.OOSKEY));
        photos.setThumbUrl(thumbMap.get(Constants.UploadDir.FILLPATH));
        photos.setThumbOss(thumbMap.get(Constants.UploadDir.OOSKEY));
        int save = photosMapper.insert(photos);
        Map<String,Object> result = new HashMap<>(2);
        result.put("result",save > 0);
        result.put("path",path);
        return result;
    }

    /**
     * 删除存储的图片
     * @param type 1是删除图片2是删除其他
     * @param keys 一个或者多个
     */
    @Override
    public void deleteByKeys(Object keys,Integer type) {
        if (ObjectUtil.isNotEmpty(keys)) {
            if (keys instanceof String) {
                deleteByKey(keys.toString(),type);
            } else if (keys instanceof String[]) {
                deleteKeys((String[]) keys,type);
            }
        }
    }

    private void deleteKeys(String[] keys,Integer type) {
        for (String key : keys) {
            deleteByKey(key,type);
        }
    }

    @Override
    public void deleteByKey(String key, Integer type) {
        String fill = new String(key);
        if (type == 1) {
            Photos byUrl = photosMapper.getByUrlAndUser(key, TokenUtil.USERID);
            if (ObjectUtil.isEmpty(byUrl)) {
                throw new BizException("没有这个图片~");
            }
            if (byUrl.getDeleteState() == 0) {
                throw new BizException("已经是删除状态了~");
            }
            key = byUrl.getOosKey();
        }
        if (RegExpUtil.checkHttps(key)) {
            int index = key.indexOf(Constants.OOSStr.QN_PRE);
            if (index != -1) {
                key = key.replace(Constants.OOSStr.QN_PRE,"");
            } else {
                int indexOf = key.indexOf("//");
                String key2 = key.substring(indexOf + 1);
                key = key2.substring(key2.indexOf("/") + 1);
            }
        }

        try {
            Response delete = qnyUtil.deleteByKey(key);
            //解析结果
            int statusCode = delete.statusCode;
            if (statusCode == 200) {
                recodeService.deleteByUrl(fill);
                if (type == 1) {
                    photosMapper.setDeleteStateByUrl(fill);
                }
                log.info("删除成功key：" + fill);
            } else {
                log.info("删除失败，服务器错误key：" + fill);
                throw new BizException("删除失败，服务器错误key：" + fill);
            }
        } catch (QiniuException e) {
            // e.printStackTrace();
            String errMsg = e.error();
            if (e.error().contains("no such file or directory")) {
                errMsg = "已删除或者是不存在此文件";
                if (type == 1) {
                    photosMapper.setDeleteStateByUrl(fill);
                }
            }
            log.error("删除七牛云资源失败原因：" + key + "~" + errMsg);
            throw new BizException("删除七牛云资源失败原因：" + key + "~" + errMsg);
        }
    }
    
}
