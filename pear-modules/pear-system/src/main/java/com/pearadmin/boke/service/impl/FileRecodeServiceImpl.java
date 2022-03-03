package com.pearadmin.boke.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pearadmin.boke.entry.FileRecode;
import com.pearadmin.boke.service.FileRecodeService;
import com.pearadmin.boke.utils.TokenUtil;
import com.pearadmin.boke.utils.contains.Constants;
import com.lsh.mapper.boke.FileRecodeMapper;
import com.lsh.mapper.boke.PhotosMapper;
import com.pearadmin.boke.vo.query.QueryFileRecodeVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author lushao
 * @Description 文件上传记录service
 * @Date 2021/6/2 17:25
 * @Version 1.0
 */
@Slf4j
@Service
public class FileRecodeServiceImpl extends ServiceImpl<FileRecodeMapper, FileRecode> implements FileRecodeService {
    
    @Autowired
    private FileRecodeMapper fileRecodeMapper;
    
    @Autowired
    private PhotosMapper photosMapper;
    
    @Override
    public int deleteByUrls(String[] keys) {
        QueryWrapper<FileRecode> wrapper = new QueryWrapper<>();
        wrapper.and(wrapper2 -> wrapper2.in("oos_key",keys).or().in("file_url",keys));
        return fileRecodeMapper.delete(wrapper);
    }

    @Override
    public int deleteByUrl(String key) {
        QueryWrapper<FileRecode> wrapper = new QueryWrapper<>();
        wrapper.and(wrapper2 -> wrapper2.eq("oos_key",key).or().eq("file_url",key));
        return fileRecodeMapper.delete(wrapper);
    }

    @Override
    public IPage<FileRecode> getRecodePage(QueryFileRecodeVo vo) {
        Page<FileRecode> page = new Page<>(vo.getPage(),vo.getLimit());
        return fileRecodeMapper.getRecodePage(page,vo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteRecodeByUrl(String urls) {
        if (StrUtil.isNotEmpty(urls)) {
            String[] split = urls.split(",");
            List<String> urlList = Arrays.asList(split);
            try {
                urlList.forEach(u -> {
                    QueryWrapper queryWrapper = new QueryWrapper();
                    queryWrapper.eq("file_url",u);
                    FileRecode fileRecode = fileRecodeMapper.selectOne(queryWrapper);
                    if (fileRecode.getFileOwnId() == 4) {
                        QueryWrapper wrapper = new QueryWrapper();
                        wrapper.eq("photo_url",u);
                        int delete = photosMapper.delete(wrapper);
                    }
                    int delete1 = fileRecodeMapper.delete(queryWrapper);
                });
                return 1;
            } catch (Exception e) {
                log.error("删除失败，回滚->{}",e.getMessage());
                //设置手动回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        }
        return 0;
    }

    /**
     *
     * @param file
     * @param showUrl 访问路径
     * @param own 属于谁的 文件所属位置1、富文本2、markdown3、头像4、相册5、其他
     * @param uploadType 上传类型 1、ftp，2txy，3阿里，4七牛
     */
    @Override
    public void saveUploadRecode(MultipartFile file, Map<String,String> showUrl, Integer own, Integer uploadType) {
        FileRecode recode = new FileRecode();
        recode.setUserId(TokenUtil.USERID);
        recode.setFileType(file.getContentType());
        recode.setFileSize(file.getSize());
        recode.setFileUrl(showUrl.get(Constants.UploadDir.FILLPATH));
        recode.setOosKey(showUrl.get(Constants.UploadDir.OOSKEY));
        recode.setFileOwnId(own);
        recode.setUploadTypeId(uploadType);
        recode.setLocalPath(file.getOriginalFilename());
        recode.setFileName(file.getName());
        fileRecodeMapper.insert(recode);
    }

    /**
     * 缩略图
     * @param key
     * @param showUrl
     * @param own
     * @param uploadType
     */
    @Override
    public void saveUploadRecode(InputStream inputStream, String key, Map<String,String> showUrl, Integer own, Integer uploadType) {
        FileRecode recode = new FileRecode();
        recode.setUserId(TokenUtil.USERID);
        recode.setFileType("缩略图");
        try {
            recode.setFileSize(inputStream.available());
        } catch (IOException e) {
            e.printStackTrace();
        }
        recode.setFileUrl(showUrl.get(Constants.UploadDir.FILLPATH));
        recode.setOosKey(showUrl.get(Constants.UploadDir.OOSKEY));
        recode.setFileOwnId(own);
        recode.setUploadTypeId(uploadType);
        recode.setFileName(key);
        fileRecodeMapper.insert(recode);
    }
}
