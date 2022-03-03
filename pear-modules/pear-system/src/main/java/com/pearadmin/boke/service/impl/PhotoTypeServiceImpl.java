package com.pearadmin.boke.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsh.mapper.boke.PhotoTypeMapper;
import com.pearadmin.boke.entry.PhotoType;
import com.pearadmin.boke.service.PhotoTypeService;
import com.pearadmin.boke.vo.SelectVo;

/**
 * @Author lushao
 * @Description
 * @Date 2021/6/9 10:32
 * @Version 1.0
 */
@Service
public class PhotoTypeServiceImpl extends ServiceImpl<PhotoTypeMapper, PhotoType> implements PhotoTypeService {
    
    @Autowired
    private PhotoTypeMapper photoTypeMapper;
    
    @Override
    public List<SelectVo> selects() {
        // List<PhotoType> photoTypes = photoTypeMapper.selectList(null);
        List<PhotoType> photoTypes = photoTypeMapper.getByUseType();
        List<SelectVo> vos = new ArrayList<>();
        if (photoTypes.isEmpty()) {
            return null;
        }
        photoTypes.forEach(e -> {
            vos.add(new SelectVo(e.getPTypeId(),e.getTypeName()));
        });
        return vos;
    }
}
