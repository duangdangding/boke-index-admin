package com.pearadmin.boke.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsh.mapper.boke.VideoSourceMapper;
import com.pearadmin.boke.entry.VideoSource;
import com.pearadmin.boke.service.VideoSourceService;
import com.pearadmin.boke.vo.query.QuerySourceVo;
import com.pearadmin.boke.vo.query.SourceSearchVo;

import cn.hutool.core.util.StrUtil;

/**
 * VideoSource接口实现层
 *
 * @author lushao
 * @version 1.0.0 2021-06-30 19:28:16
 */
@Service
public class VideoSourceServiceImpl extends ServiceImpl<VideoSourceMapper, VideoSource> implements VideoSourceService {

    @Autowired
    private VideoSourceMapper videoSourceMapper;

    @Override
    public List<VideoSource> getByApi(String api) {
        QueryWrapper<VideoSource> wrapper = new QueryWrapper<>();
        wrapper.eq("source_api",api);
//        wrapper.like("source_api",api);
        return list(wrapper);
    }

    @Override
    public IPage<VideoSource> getVideoSourcePage(QuerySourceVo vo) {
        Page page = new Page(vo.getPage(),vo.getLimit());
        List<VideoSource> listBywebAvai = videoSourceMapper.getListBywebAvai(page,vo);
        page.setRecords(listBywebAvai);
        return page;
    }

    @Override
    public int setShoudong(String ids) {
        if (StrUtil.isNotEmpty(ids)) {
            String[] split = ids.split(",");
            QueryWrapper<VideoSource> queryWrapper = new QueryWrapper();
            // string数组转list<Integer>
            List<Integer> idList = Arrays.stream(split)
                    .map(s->Integer.parseInt(s.trim()))
                    .collect(Collectors.toList());
            queryWrapper.in("source_id",idList);
            VideoSource videoSource = new VideoSource();
            videoSource.setShoudong(0);
            return videoSourceMapper.update(videoSource,queryWrapper);
        }
        return 0;
    }

    @Override
    public int setType(String ids,Integer type) {
        if (StrUtil.isNotEmpty(ids)) {
            String[] split = ids.split(",");
            QueryWrapper<VideoSource> queryWrapper = new QueryWrapper();
            // string数组转list<Integer>
            List<Integer> idList = Arrays.stream(split)
                    .map(s->Integer.parseInt(s.trim()))
                    .collect(Collectors.toList());
            queryWrapper.in("source_id",idList);
            VideoSource videoSource = new VideoSource();
            videoSource.setSourceType(type);
            return videoSourceMapper.update(videoSource,queryWrapper);
        }
        return 0;
    }

    @Override
    public int setJx(String ids) {
        if (StrUtil.isNotEmpty(ids)) {
            String[] split = ids.split(",");
            QueryWrapper<VideoSource> queryWrapper = new QueryWrapper();
            // string数组转list<Integer>
            List<Integer> idList = Arrays.stream(split)
                    .map(s->Integer.parseInt(s.trim()))
                    .collect(Collectors.toList());
            queryWrapper.in("source_id",idList);
            VideoSource videoSource = new VideoSource();
            videoSource.setNeedParse(1);
            return videoSourceMapper.update(videoSource,queryWrapper);
        }
        return 0;
    }

    @Override
    public int setDeleteState(String ids) {
        if (StrUtil.isNotEmpty(ids)) {
            String[] split = ids.split(",");
            QueryWrapper<VideoSource> queryWrapper = new QueryWrapper();
            // string数组转list<Integer>
            List<Integer> idList = Arrays.stream(split)
                    .map(s->Integer.parseInt(s.trim()))
                    .collect(Collectors.toList());
            queryWrapper.in("source_id",idList);
            VideoSource videoSource = new VideoSource();
            videoSource.setWebAvailable(1);
            return videoSourceMapper.update(videoSource,queryWrapper);
        }
        return 0;
    }

    @Override
    public List<VideoSource> getByUse() {
        QueryWrapper<VideoSource> wrapper = new QueryWrapper<>();
        // wrapper.orderByDesc("create_time");
        wrapper.eq("source_see",1);
        return list(wrapper);
    }

    @Override
    public Map<String, Object> countBycause() {
        Map<String, Object> map = videoSourceMapper.countBycause();
        String ty = (String) map.get("ty");
        String[] split = ty.split(",");
        map.put("tys",split);
        return map;
    }

    /**
     * 1全部可用2正常3有色
     * @param type
     * @return
     */
    @Override
    public List<VideoSource> getByType(int type) {
        QueryWrapper<VideoSource> wrapper = new QueryWrapper<>();
        // wrapper.orderByDesc("create_time");
        wrapper.eq("source_see",1);
        if (2 == type) {
            wrapper.eq("source_type",1);
        } else if (3 == type) {
            wrapper.eq("source_type",2);
        }
        return list(wrapper);
    }
    @Override
    public List<VideoSource> getByuType(SourceSearchVo svo) {
        return videoSourceMapper.getByuType(svo);
    }

    @Override
    public List<VideoSource> getZyplayers(QuerySourceVo vo) {
        return videoSourceMapper.getZyplayers(vo);
    }

    @Override
    public List<VideoSource> getListBywebAvai(QuerySourceVo vo) {
        return videoSourceMapper.getListBywebAvai(null,vo);
    }

    @Override
    public int updateSetJxName(VideoSource videoSource) {
        return videoSourceMapper.updateSetJxName(videoSource);
    }

    @Override
    public int updateUnSetJxName(VideoSource videoSource) {
        return videoSourceMapper.updateUnSetJxName(videoSource);
    }
    
    @Override
    public int updateSetYellowName(VideoSource videoSource) {
        return videoSourceMapper.updateSetYellowName(videoSource);
    }

    @Override
    public int updateUnSetYellowName(VideoSource videoSource) {
        return videoSourceMapper.updateUnSetYellowName(videoSource);
    }
}