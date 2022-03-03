package com.pearadmin.boke.ctr.admin;

import java.util.Map;
import java.util.Set;

import com.pearadmin.boke.service.FileRecodeService;
import com.pearadmin.boke.service.PhotosService;
import com.pearadmin.boke.service.UploadFileService;
import com.pearadmin.boke.utils.*;
import com.pearadmin.boke.utils.upload.QnyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pearadmin.boke.utils.contains.BaseCtr;
import com.pearadmin.boke.utils.contains.Constants;
import com.pearadmin.boke.utils.contains.PassToken;
import com.pearadmin.boke.utils.contains.UserLoginToken;
import com.pearadmin.boke.vo.ResultDto;

import cn.hutool.core.util.StrUtil;

@RestController
@RequestMapping("admin/other")
public class OtherCtr extends BaseCtr {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private QnyUtil qnyUtil;
    @Autowired
    private UploadFileService uploadFileService;
    @Autowired
    private FileRecodeService fileRecodeService;
    @Autowired
    private PhotosService photosService;

    /**
     * 删除redis数据
     * @param delType 1 删除单个 2 根据前缀删除多个 3，根据后缀删除多个
     * @param delKey
     * @return
     */
    @RequestMapping("/delRedis")
    public ResultDto delRedisDataByKey(Integer delType,String delKey) {
        String[] keys = delKey.split(",");
        try {
            if (delType == 1) {
                redisUtil.del(keys);
            } else if (delType == 2) {
                for (String key : keys) {
                    redisUtil.preDel(key);
                }
            } else {
                for (String key : keys) {
                    redisUtil.sufDel(key);
                }
            }
            return success();
        } catch (Exception e) {
            return fail();
        }
    }

    @RequestMapping("/word/list")
    public ResultDto violationWordList() {
        String key = Constants.RedisKey.VIOLATION;
        Set<Object> objects = redisUtil.sGet(key);
        return success(objects);
    }
    @RequestMapping("/ranname/list")
    public ResultDto rannameList() {
        String key = Constants.RedisKey.randomName;
        Set<Object> objects = redisUtil.sGet(key);
        return success(objects);
    }

    @RequestMapping("/word/add")
    public ResultDto violationWordAdd(String word) {
        String key = Constants.RedisKey.VIOLATION;
        if (redisUtil.sHasKey(key,word)) {
            return fail(EXISIT);
        }
        long l = redisUtil.sSet(key, word);
        return returnDto(l);
    }
    @RequestMapping("/ranname/add")
    public ResultDto rannameAdd(String word) {
        String key = Constants.RedisKey.randomName;
        if (redisUtil.sHasKey(key,word)) {
            return fail(EXISIT);
        }
        long l = redisUtil.sSet(key, word);
        return returnDto(l);
    }

    @RequestMapping("/word/del")
    public ResultDto violationWordDel(String word) {
        String key = Constants.RedisKey.VIOLATION;
        if (redisUtil.hasKey(key)) {
            long l = redisUtil.setRemove(key, word);
            return returnDto(l);
        }
        return success();
    }
    @RequestMapping("/ranname/del")
    public ResultDto rannameDel(String word) {
        String key = Constants.RedisKey.randomName;
        if (redisUtil.hasKey(key)) {
            long l = redisUtil.setRemove(key, word);
            return returnDto(l);
        }
        return success();
    }

    /**
     * 上传相册
     * @param image
     * @param photoType
     * @return
     * @throws Exception
     */
    // @PassToken
    @UserLoginToken
    @PostMapping("/t/kodo/phc")
    public ResultDto<String> photoUpload(@RequestParam(value = "photo-image-file",required = false) MultipartFile image, Integer photoType) throws Exception {
        long size = image.getSize();
        if (size > Constants.UploadFileSize.PHOTOIMGMAXSIZE || size == 0) {
            return fail(FILE_10M);
        }

        Map<String, Object> result = uploadFileService.uploadPhotoReturnPath(image, photoType);
        Boolean save = (Boolean) result.get("result");
        if (save) {
            return success(result.get("path"));
        } else {
            return fail(SCSB);
        }
    }

    /**
     * 上传其他文件
     * @param file
     * @param floder
     * @param fileName
     * @return
     * @throws Exception
     */
    // @UserLoginToken
    @PassToken
    @PostMapping("/t/kodo/other")
    public ResultDto<String> fileUpload(@RequestParam(value = "file",required = false) MultipartFile file,String floder,String fileName) throws Exception {
        long size = file.getSize();
        if (size > Constants.UploadFileSize.FILEMAXSIZE || size == 0) {
            return fail(FILE_200M);
        }
        // 开始上传
        Map<String,String> map = qnyUtil.updateFile(file, Constants.UploadDir.OTHER + floder,fileName);
        fileRecodeService.saveUploadRecode(file,map,Constants.UploadDir.OWN_OT,Constants.UploadDir.OSSTYPE_QN);
        String path = map.get(Constants.UploadDir.FILLPATH);
        if (StrUtil.isNotEmpty(path)) {
            return success(path);
        } else {
            return fail(SCSB);
        }
    }

    /**
     * 删除图片
     * @param keys
     * @return
     */
    @PassToken
    @RequestMapping("/t/del/kodo")
    public ResultDto deleteBykey(String[] keys) {
        uploadFileService.deleteByKeys(keys,1);
        return success();
    }

    /**
     * 删除其他文件
     * @param keys
     * @return
     */
    @PassToken
    @RequestMapping("//t/del/kodo2")
    public ResultDto deleteOtherBykey(String[] keys) {
        uploadFileService.deleteByKeys(keys,2);
        return success();
    }
}
