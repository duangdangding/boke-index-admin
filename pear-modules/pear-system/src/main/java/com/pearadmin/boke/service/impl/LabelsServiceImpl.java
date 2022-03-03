package com.pearadmin.boke.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pearadmin.boke.entry.Labels;
import com.lsh.mapper.boke.LabelsMapper;
import com.pearadmin.boke.service.LabelsService;
import com.pearadmin.boke.utils.RedisUtil;
import com.pearadmin.boke.utils.contains.Constants;
import com.pearadmin.boke.vo.query.QueryLabelVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;

@Service
public class LabelsServiceImpl extends ServiceImpl<LabelsMapper, Labels> implements LabelsService {
    
    @Autowired
    private LabelsMapper labelsMapper;
    
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public List<Labels> getByUserId(Integer userId) {
        String key = Constants.RedisKey.LABELLIST + userId;
        // String key = Constants.RedisKey.LABELRANK;
        if (redisUtil.hasKey(key)) {
            return JSONUtil.toList(redisUtil.get(key).toString(),Labels.class);
        }
        List<Labels> byUserId = labelsMapper.getByUserId(userId);
        redisUtil.set(key,JSONUtil.toJsonStr(byUserId));
        return byUserId;
    }

    @Override
    public IPage<Labels> getLabelPage(QueryLabelVo vo) {
        Page page = new Page(vo.getPage(),vo.getLimit());
        QueryWrapper queryWrapper = new QueryWrapper();
        String labelName = vo.getLabelName();
        if (StrUtil.isNotEmpty(labelName)) {
            queryWrapper.like("label_name",labelName);
        }
        if (vo.getDeleteState() != null) {
            queryWrapper.eq("delete_state",vo.getDeleteState());
        }
        queryWrapper.orderByDesc("delete_state","create_time");
        return labelsMapper.selectPage(page,queryWrapper);
    }

    @Override
    public Integer getByLabelName(String labelName) {
        QueryWrapper<Labels> wrapper = new QueryWrapper<>();
        wrapper.eq("label_name",labelName);
        Labels labels = labelsMapper.selectOne(wrapper);
        return labels == null ? null : labels.getLabelId();
    }

    @Override
    public List<Labels> labelRank() {
        String key = Constants.RedisKey.LABELRANK;
        if (redisUtil.hasKey(key)) {
            return JSONUtil.toList(redisUtil.get(key).toString(),Labels.class);
        }
        List<Labels> byUserLabel = labelsMapper.labelRank();
        redisUtil.set(key,JSONUtil.toJsonStr(byUserLabel));
        return byUserLabel;
    }

    @Override
    public int getLabelCountByBoke(Integer labelId) {
        return labelsMapper.getLabelCountByBoke(labelId);
    }

    @Override
    public int setDeleteState(String ids,Integer deleteState) {
        if (StrUtil.isNotEmpty(ids)) {
            String[] split = ids.split(",");
            QueryWrapper<Labels> queryWrapper = new QueryWrapper();
            // string数组转list<Integer>
            List<Integer> idList = Arrays.stream(split)
                    .map(s->Integer.parseInt(s.trim()))
                    .collect(Collectors.toList());
            queryWrapper.in("label_id",idList);
            Labels labels = new Labels();
            labels.setDeleteState(deleteState);
            int update = labelsMapper.update(labels, queryWrapper);
            if (update > 0) {
                redisUtil.preDel(Constants.RedisKey.LABELLIST);
            }
            return update;
        }
        return 0;
    }

    @Override
    public List<Labels> getUseLabels() {
        String key = Constants.RedisKey.USELABELLIST;
        if (redisUtil.hasKey(key)) {
            return JSONUtil.toList(redisUtil.get(key).toString(), Labels.class);
        }
        QueryWrapper<Labels> wrapper = new QueryWrapper<>();
        wrapper.eq("delete_state",1);
        List<Labels> list = list(wrapper);
        redisUtil.set(key ,JSONUtil.toJsonStr(list));
        return list;
    }
}
