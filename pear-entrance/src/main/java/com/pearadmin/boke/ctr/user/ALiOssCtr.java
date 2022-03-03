package com.pearadmin.boke.ctr.user;

import com.pearadmin.boke.service.PhotosService;
import com.pearadmin.boke.service.UsersService;
import com.pearadmin.boke.entry.Photos;
import com.pearadmin.boke.entry.Users;
import com.pearadmin.boke.utils.TokenUtil;
import com.pearadmin.boke.utils.contains.BaseCtr;
import com.pearadmin.boke.utils.contains.UserLoginToken;
import com.pearadmin.boke.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author lushao
 * @Description 阿里云OSS
 * @Date 2021/6/5 9:49
 * @Version 1.0
 */
public class ALiOssCtr extends BaseCtr {

    @Autowired
    private UsersService usersService;
    @Autowired
    private PhotosService photosService;

    /**
     * 上传文件至阿里云 oss
     *  WangEditor 富文本编辑器
     * @param files
     * @param uploadKey
     * @return
     * @throws Exception
     */
    @UserLoginToken
    @RequestMapping(value = "/t/oss/upload", method = {RequestMethod.POST}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResultDto<List<Map>> uploadOSS1(@RequestParam(value = "files") List<MultipartFile> files, String uploadKey) throws Exception {

        if (TokenUtil.USERID == null) {
            return ResultDtoManager.fail(-1,"请先登录~");
        }
//    public ResultDto<List<Map>> uploadOSS(@RequestParam(value = "editormd-image-file",required = false) List<MultipartFile> files, String uploadKey) throws Exception {
        List<Map> maps = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            // Map apiResult = commonService.uploadOSS(files.get(i), Constants.UploadDir.WD);
            // maps.add(apiResult);
        }
        return ResultDtoManager.success(maps);
    }

    @UserLoginToken
    @RequestMapping(value = "/t/oss/upload/fc", method = {RequestMethod.POST}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResultDto<String> uploadOSSface(@RequestParam(value = "fcIMg") MultipartFile fcIMg) throws Exception {

        if (TokenUtil.USERID == null) {
            return ResultDtoManager.fail(-1,"请先登录~");
        }
        // Map apiResult = commonService.uploadOSS(fcIMg, Constants.UploadDir.FACE);
        Users users = new Users();
        users.setUserId(TokenUtil.USERID);
        // users.setUserFace((String) apiResult.get("ossFileUrlBoot"));
        int i = usersService.setFace(users);
        return returnDto(i);
    }

    /**
     * 上传文件至阿里云 oss
     *  Editormd markdown编辑器
     * @param image
     * @param uploadKey
     * @return
     * @throws Exception
     */
    @UserLoginToken
    @RequestMapping(value = "/t/oss/upload/md", method = {RequestMethod.POST}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public EditorUploadEntry uploadOSS2(@RequestParam(value = "editormd-image-file",required = false) MultipartFile image, String uploadKey) throws Exception {
        // Map apiResult = commonService.uploadOSS(image, Constants.UploadDir.MD);
        // EditorUploadEntry ossFileUrlBoot = new EditorUploadEntry(1, "", (String) apiResult.get("ossFileUrlBoot"));
        // return ossFileUrlBoot;
        return null;
    }

    //    @UserLoginToken
    @PostMapping("/oss/upload/phc")
    public ResultDto<String> uploadPhotos(@RequestParam(value = "photo-image-file",required = false) MultipartFile image) throws Exception {
        // Map apiResult = commonService.uploadOSS(image, Constants.UploadDir.PHOTOS + "/" + "anime");
        Photos photos = new Photos();
        photos.setPhotoType(1);
        photos.setUserId(2);
        photos.setPhotoTitle("动漫2");
        // String ossFileUrlBoot = (String) apiResult.get("ossFileUrlBoot");
        // photos.setPhotoUrl(ossFileUrlBoot);
        boolean save = photosService.save(photos);
        if (save) {
            // return ResultDtoManager.success(ossFileUrlBoot);
            return null;
        } else {
            return ResultDtoManager.fail("上传失败~");
        }

    }
    
    
}
