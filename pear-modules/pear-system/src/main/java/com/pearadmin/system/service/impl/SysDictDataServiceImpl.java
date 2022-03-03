package com.pearadmin.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.pearadmin.common.tools.SpringUtil;
import com.pearadmin.common.web.domain.request.PageDomain;
import com.pearadmin.system.domain.SysDictData;
import com.pearadmin.system.mapper.SysDictDataMapper;
import com.pearadmin.system.service.ISysDictDataService;

/**
 * Describe: 字典值服务实现类
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 */
@Service
public class SysDictDataServiceImpl implements ISysDictDataService {

    public static LoadingCache<String, List<SysDictData>> loadingCacheSysDictData = CacheBuilder.newBuilder().maximumSize(100).expireAfterWrite(600, TimeUnit.SECONDS).build(new CacheLoader<String, List<SysDictData>>() {
        @Override
        public List<SysDictData> load(String typeCode) {
            SysDictDataMapper tempSysDictDataMapper = SpringUtil.getBean("sysDictDataMapper", SysDictDataMapper.class);
            return tempSysDictDataMapper.selectByCode(typeCode);
        }
    });
    @Resource
    private SysDictDataMapper sysDictDataMapper;

    @Override
    public List<SysDictData> list(SysDictData sysDictData) {
        return sysDictDataMapper.selectList(sysDictData);
    }

    @Override
    public List<SysDictData> selectByCode(String typeCode) {
        try {
            return loadingCacheSysDictData.get(typeCode);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();

    }

    @Override
    public void refreshCacheTypeCode(String typeCode) {
        try {
            loadingCacheSysDictData.refresh(typeCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public IPage<SysDictData> page(SysDictData sysDictData, PageDomain pageDomain) {
        Page<SysDictData> page = new Page<>(pageDomain.getPage(), pageDomain.getLimit());
        return sysDictDataMapper.selectList(page,sysDictData);
    }
/*
    @Override
    public PageInfo<SysDictData> page(SysDictData sysDictData, PageDomain pageDomain) {
        PageHelper.startPage(pageDomain.getPage(), pageDomain.getLimit());
        List<SysDictData> list = sysDictDataMapper.selectList(sysDictData);
        return new PageInfo<>(list);
    }
*/

    @Override
    public Boolean save(SysDictData sysDictData) {
        Integer result = sysDictDataMapper.insert(sysDictData);
        if (result > 0) {
            refreshCacheTypeCode(sysDictData.getTypeCode());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public SysDictData getById(String id) {
        return sysDictDataMapper.selectById(id);
    }

    @Override
    public Boolean updateById(SysDictData sysDictData) {
        int result = sysDictDataMapper.updateById(sysDictData);
        if (result > 0) {
            refreshCacheTypeCode(sysDictData.getTypeCode());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean remove(String id) {
        SysDictData sysDictData = sysDictDataMapper.selectById(id);
        if (sysDictData != null) {
            sysDictDataMapper.deleteById(id);
            refreshCacheTypeCode(sysDictData.getTypeCode());
        }
        return true;
    }

    @Override
    public List<SysDictData> queryTableDictItemsByCode(String table, String text, String code) {
        return sysDictDataMapper.queryTableDictItemsByCode(table, text, code);
    }

    @Override
    public List<SysDictData> queryTableDictItemsByCodeAndFilter(String table, String text, String code, String filterSql) {
        return sysDictDataMapper.queryTableDictItemsByCodeAndFilter(table, text, code, filterSql);
    }

    @Override
    public List<SysDictData> queryTableDictByKeys(String table, String text, String code, String[] keyArray) {
        return sysDictDataMapper.queryTableDictByKeys(table, text, code, keyArray);
    }


}
