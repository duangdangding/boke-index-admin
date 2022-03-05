package com.pearadmin.boke.ctr.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pearadmin.boke.service.FileRecodeService;
import com.pearadmin.boke.utils.contains.BaseCtr;
import com.pearadmin.boke.utils.contains.Constants;
import com.pearadmin.boke.utils.upload.QnyUtil;
import com.pearadmin.boke.vo.EditorUploadEntry;
import com.pearadmin.boke.vo.ResultDto;
import com.pearadmin.common.tools.SecurityUtil;
import com.pearadmin.system.domain.SysUser;
import com.pearadmin.system.service.ISysUserService;

import cn.hutool.core.map.MapUtil;
import lombok.AllArgsConstructor;

/**
 * 七牛云存储管理
 */
@RestController
@AllArgsConstructor
public class QNYCtr extends BaseCtr {

    private final QnyUtil qnyUtil;
    private final FileRecodeService uploadUtil;
    private final ISysUserService usersService;

    /**
     * 七牛云上传富文本图片
      * @param files
     * @return
     */
    @PostMapping(value = "/t/kodo/wd",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResultDto<List<Map<String,String>>> wdUpload(@RequestParam(value = "files") List<MultipartFile> files) {
        SysUser sysUser = SecurityUtil.currentUser();
        if (!files.isEmpty()) {
            
            List<Map<String,String>> imgs = new ArrayList<>();
            // int index = 0;
            for (MultipartFile file : files) {
                long size = file.getSize();
                if (size < Constants.UploadFileSize.BOKEIMGMAXSIZE && size > 0) {
                    Map<String,String> s = qnyUtil.upload2Qiniu(file, Constants.UploadDir.WD);
                    if (MapUtil.isEmpty(s)) {
                        return fail(UWJERR);
                    }
                    uploadUtil.saveUploadRecode(file,s,Constants.UploadDir.OWN_WD,Constants.UploadDir.OSSTYPE_QN,sysUser.getUserId());
                    Map<String,String> img = new HashMap<>();
                    img.put("imgUrl",s.get(Constants.UploadDir.FILLPATH));
                    imgs.add(img);
                    // index ++;
                }
            }
            if (imgs.size() > 0) {
                return success(imgs);
            } else {
                return fail(UWJERR);
            }
        }
        return fail(FILENULL);
    }

    /**
     * markdown编辑器上传图片
     * @param image
     * @return
     */
    @PostMapping(value = "/t/kodo/md",produces = {MediaType.APPLICATION_JSON_VALUE})
    public EditorUploadEntry mdUpload(@RequestParam(value = "editormd-image-file",required = false) MultipartFile image) {
        SysUser sysUser = SecurityUtil.currentUser();
        if (sysUser == null) {
            return new EditorUploadEntry(0, LOGIN, null);
        }
        long size = image.getSize();
        if (size > Constants.UploadFileSize.BOKEIMGMAXSIZE || size == 0) {
            return new EditorUploadEntry(0, FILE5M, null);
        }
        Map<String,String> s = qnyUtil.upload2Qiniu(image, Constants.UploadDir.MD);
        uploadUtil.saveUploadRecode(image,s,Constants.UploadDir.OWN_MD,Constants.UploadDir.OSSTYPE_QN, sysUser.getUserId());
        return new EditorUploadEntry(1, "", s.get(Constants.UploadDir.FILLPATH));
    }

    /**
     * 上传头像
     * @param file
     * @return
     */
    @PostMapping(value = "/t/kodo/face",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResultDto<String> faceUpload(@RequestParam(value = "fcIMg") MultipartFile file) {
        SysUser sysUser = SecurityUtil.currentUser();
        if (sysUser == null) {
            return fail(LOGIN);
        }
        Long userId = sysUser.getUserId();
        long size = file.getSize();
        if (size > Constants.UploadFileSize.FACEIMGMAXSIZE || size == 0) {
            return fail(FILE5M);
        }
        Map<String,String> s = qnyUtil.upload2Qiniu(file, Constants.UploadDir.FACE);
        uploadUtil.saveUploadRecode(file,s,Constants.UploadDir.OWN_FA,Constants.UploadDir.OSSTYPE_QN,userId);
        SysUser users = new SysUser();
        users.setUserId(userId);
        users.setAvatar(s.get(Constants.UploadDir.FILLPATH));
        int i = usersService.setFace(users);
        return returnDto(i);
    }

}
