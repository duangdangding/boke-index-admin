package com.pearadmin.boke.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pearadmin.boke.entry.Categorys;
import com.lsh.mapper.boke.CategorysMapper;
import com.pearadmin.boke.service.CategorysService;
import com.pearadmin.boke.vo.query.QueryTypeVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;

@Service
public class CategorysServiceImpl extends ServiceImpl<CategorysMapper, Categorys> implements CategorysService {
    
   @Autowired
   private CategorysMapper categorysMapper;

    @Override
    public long addCate(Categorys categorys) {
//        return categorysMapper.addCate(categorys);
        return 0;
    }

    @Override
    public IPage<Categorys> getTypePage(QueryTypeVo vo) {
        Page page = new Page(vo.getPage(),vo.getLimit());
        QueryWrapper queryWrapper = new QueryWrapper();
        String typeName = vo.getTypeName();
        if (StrUtil.isNotEmpty(typeName)) {
            queryWrapper.like("cate_name",typeName);
        }
        if (vo.getDeleteState() != null) {
            queryWrapper.eq("delete_state",vo.getDeleteState());
        }
        queryWrapper.orderByDesc("delete_state","create_time");
        return categorysMapper.selectPage(page,queryWrapper);
    }

    @Override
    public int setDeleteState(List<Long> ids,Integer deleteState) {
        if (CollectionUtil.isNotEmpty(ids)) {
            QueryWrapper<Categorys> queryWrapper = new QueryWrapper();
            // string数组转list<Integer>
            queryWrapper.in("cate_id",ids);
            Categorys categorys = new Categorys();
            categorys.setDeleteState(deleteState);
            return categorysMapper.update(categorys,queryWrapper);
        }
        return 0;
    }
}
