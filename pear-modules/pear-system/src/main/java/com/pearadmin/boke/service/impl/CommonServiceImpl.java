package com.pearadmin.boke.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pearadmin.boke.entry.Comments;
import com.lsh.mapper.boke.CommontsMapper;
import com.lsh.mapper.boke.ReplysMapper;
import com.pearadmin.boke.service.CommonService;
import com.pearadmin.boke.vo.query.QueryCommontVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CommonServiceImpl implements CommonService {
    
    @Autowired
    private CommontsMapper commontsMapper;
    
    @Autowired
    private ReplysMapper replysMapper;

    @Override
    public IPage<Comments> getCommontPage(QueryCommontVo vo) {
        Page page = new Page(vo.getPage(),vo.getLimit());
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.orderByDesc("comm_time");
        return commontsMapper.selectPage(page,wrapper);
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public int commontDel(String ids) {
        if (StrUtil.isNotEmpty(ids)) {
            String[] split = ids.split(",");
            List<Integer> idList = Arrays.stream(split)
                    .map(s->Integer.parseInt(s.trim()))
                    .collect(Collectors.toList());
            try {
                for (Integer cId : idList) {
                    List<Integer> idsBycId = replysMapper.getIdsBycId(cId);
                    replysMapper.deleteBatchIds(idsBycId);
                }
                return commontsMapper.deleteBatchIds(idList);
            } catch (Exception e) {
                log.error("删除失败，回滚->{}",e.getMessage());
                //设置手动回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        }
        return 0;
    }
}
