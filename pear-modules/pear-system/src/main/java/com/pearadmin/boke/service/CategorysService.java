package com.pearadmin.boke.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pearadmin.boke.entry.Categorys;
import com.pearadmin.boke.vo.query.QueryTypeVo;

public interface CategorysService extends IService<Categorys> {
    long addCate(Categorys categorys);

    IPage<Categorys> getTypePage(QueryTypeVo vo);

    int setDeleteState(List<Long> ids, Integer deleteState);
}
