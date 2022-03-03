package com.pearadmin.boke.utils.upload;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import com.pearadmin.boke.utils.contains.Constants;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class QnyUtil {

    @Value("${qny.imgpre}")
    private String imgpre;
    @Value("${qny.access}")
    private String access;
    @Value("${qny.select}")
    private String select;
    @Value("${qny.bucket}")
    private String bucket;

    /**
     * 七牛云存储的服务端是一个 key-value 系统，而非树形结构，因此也没有 “目录” 或者 “文件夹” 的概念。
     * 其中，value 是用户上传到七牛云存储的文件，key 是一个用户自定义的字符串，用于在服务端标识这个 value 这个文件。
     * 一个 key 对应一个 value，因此，在每个空间（Bucket）中，key 必须是唯一的。
     * key 中可以包含斜杠 “/”，让你感觉起来像目录结构，比如 “a/b/c/d.txt” 这个 key，在服务端只对应一个文件，但它看起来像 a 目录下的 b 目录下的 c 目录下的文件 d.txt。
     * 实际上，服务端是不存在 a、b、c 三个目录的，也没法创建目录。
     * key 最大长度是750字节。如全为英文，可包含 750 字符。
     * key 一般不以斜线(/)作为第一个字符，不要包含其它特殊字符。最好只包含英文字母、数字、斜线(/)、点(.)等普通字符。
     * key 区分大小写。
     * @param file
     * @param floder
     * @return
     */
    public Map<String,String> upload2Qiniu(MultipartFile file, String floder) {
        //构造一个带指定Zone对象的配置类,Zone.zone0()代表华东地区
        //zone2() 华南
//        Configuration cfg = new Configuration(Zone.zone1());
        Configuration cfg = new Configuration(getRegion());

        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        // 文件名格式 毫秒+UUID
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
        String uploadFileName = System.currentTimeMillis() + UUID.randomUUID().toString().substring(0,18) + suffix;
        String key = floder +  Constants.DateFormat.LDATE.format(new Date()) + "/" + uploadFileName.replace("-","");
        Auth auth = Auth.create(access, select);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(file.getInputStream(), key, upToken, null, null);
            //解析上传成功的结果
            String bodyString = response.bodyString();
            JSONObject jsonObject = JSONUtil.parseObj(bodyString);
            String hash = (String) jsonObject.get("key");
            if (StringUtils.isNotEmpty(hash)) {
                Map<String,String> map = new HashMap<>();
                map.put(Constants.UploadDir.FILLPATH,imgpre + key);
                map.put(Constants.UploadDir.OOSKEY, key);
                return map;
            }
            // 访问路径
        } /*catch (QiniuException ex) {
            ex.error()
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
                return null;
            } catch (QiniuException ex2) {
                //ignore
                ex.printStackTrace();
                return null;
            }
        } */catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
    /**
     * @Author lushao
     * @Description //TODO 
     * @Date 10:53 2021/12/11
     * @Param [inputStream, floder, key]
     * @return Map<String,String>
     **/
    public Map<String,String> upload2QiniuStream(InputStream inputStream, String key) {
        Configuration cfg = new Configuration(getRegion());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(access, select);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(inputStream, key, upToken, null, null);
            //解析上传成功的结果
            String bodyString = response.bodyString();
            JSONObject jsonObject = JSONUtil.parseObj(bodyString);
            String hash = (String) jsonObject.get("key");
            if (StringUtils.isNotEmpty(hash)) {
                Map<String,String> map = new HashMap<>();
                map.put(Constants.UploadDir.FILLPATH,imgpre + key);
                map.put(Constants.UploadDir.OOSKEY, key);
                return map;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 上传文件 
     * @param file 要上传的文件
     * @param floder 文件夹
     * @param fileName 文件名
     * @return
     */
    public Map<String,String> updateFile(MultipartFile file, String floder,String fileName) {
        Configuration cfg = new Configuration(getRegion());
        UploadManager uploadManager = new UploadManager(cfg);
        String filename = file.getOriginalFilename();
        if (StrUtil.isNotEmpty(fileName)) {
            String suffix = file.getOriginalFilename().substring(filename.lastIndexOf('.'));
            filename = fileName + suffix;
        }
        filename = filename.replace(" ","_");
        String key = floder +  filename;
        Auth auth = Auth.create(access, select);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(file.getInputStream(), key, upToken, null, null);
            //解析上传成功的结果
            String bodyString = response.bodyString();
            JSONObject jsonObject = JSONUtil.parseObj(bodyString);
            String hash = (String) jsonObject.get("key");
            if (StringUtils.isNotEmpty(hash)) {
                if (StringUtils.isNotEmpty(hash)) {
                    Map<String,String> map = new HashMap<>();
                    map.put(Constants.UploadDir.FILLPATH,imgpre + key);
                    map.put(Constants.UploadDir.OOSKEY, key);
                    return map;
                }
            }
            // 访问路径
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    /**
     * 根据类型得到bucket
     * @return
     */
    private Region getRegion() {
        if (bucket.contains(Constants.OOSStr.HN)) {
            return Region.huanan();
        }
        return Region.huabei();
    }
    
    public Response deleteByKey(String key) throws QiniuException {
        Configuration cfg = new Configuration(getRegion());
        Auth auth = Auth.create(access, select);
        BucketManager deleteManager = new BucketManager(auth,cfg);
        return deleteManager.delete(bucket, key);
    }

}
