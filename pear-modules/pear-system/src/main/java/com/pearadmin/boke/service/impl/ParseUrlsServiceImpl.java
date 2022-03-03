package com.pearadmin.boke.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsh.mapper.boke.ParseUrlsMapper;
import com.pearadmin.boke.entry.ParseUrls;
import com.pearadmin.boke.service.ParseUrlsService;
import com.pearadmin.boke.vo.query.QuerySourceVo;

import cn.hutool.core.util.StrUtil;

/**
* ParseUrls接口实现层
*
* @author lushao
* @version 1.0.0 2021-07-03 11:13:41
*/
@Service
public class ParseUrlsServiceImpl extends ServiceImpl<ParseUrlsMapper, ParseUrls> implements ParseUrlsService {
    
    @Autowired
    private ParseUrlsMapper parseUrlsMapper;

    @Override
    public List<ParseUrls> getByApi(String api) {
        QueryWrapper<ParseUrls> wrapper = new QueryWrapper<>();
        wrapper.eq("parse_url",api);
        return list(wrapper);
    }

    @Override
    public IPage<ParseUrls> getParsePage(QuerySourceVo vo) {
        Page page = new Page(vo.getPage(),vo.getLimit());
        List<ParseUrls> listByPage = parseUrlsMapper.getListByPage(page, vo);
        page.setRecords(listByPage);
        return page;
    }

    @Override
    public int setShoudong(String ids) {
        if (StrUtil.isNotEmpty(ids)) {
            String[] split = ids.split(",");
            QueryWrapper<ParseUrls> queryWrapper = new QueryWrapper();
            // string数组转list<Integer>
            List<Integer> idList = Arrays.stream(split)
                    .map(s->Integer.parseInt(s.trim()))
                    .collect(Collectors.toList());
            queryWrapper.in("parse_id",idList);
            ParseUrls parseUrls = new ParseUrls();
            parseUrls.setShoudong(0);
            return parseUrlsMapper.update(parseUrls,queryWrapper);
        }
        return 0;
    }

    @Override
    public int setDeleteState(String ids) {
        if (StrUtil.isNotEmpty(ids)) {
            String[] split = ids.split(",");
            QueryWrapper<ParseUrls> queryWrapper = new QueryWrapper();
            // string数组转list<Integer>
            List<Integer> idList = Arrays.stream(split)
                    .map(s->Integer.parseInt(s.trim()))
                    .collect(Collectors.toList());
            queryWrapper.in("parse_id",idList);
            ParseUrls parseUrls = new ParseUrls();
            parseUrls.setParseActive(0);
            return parseUrlsMapper.update(parseUrls,queryWrapper);
        }
        return 0;
    }
}