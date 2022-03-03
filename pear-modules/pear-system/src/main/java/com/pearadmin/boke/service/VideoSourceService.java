package com.pearadmin.boke.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pearadmin.boke.entry.VideoSource;
import com.pearadmin.boke.vo.query.QuerySourceVo;
import com.pearadmin.boke.vo.query.SourceSearchVo;

/**
* VideoSource接口层
*
* @author lushao
* @version 1.0.0 2021-06-30 19:28:16
*/
public interface VideoSourceService extends IService<VideoSource> {
    
    List<VideoSource> getByApi(String api);
    
    IPage<VideoSource> getVideoSourcePage(QuerySourceVo vo);
    
    int setShoudong(String ids);
    int setType(String ids,Integer type);
    int setJx(String ids);
    int setDeleteState(String ids);
    
    List<VideoSource> getByUse();

    Map<String,Object> countBycause();

    List<VideoSource> getByType(int type);
    List<VideoSource> getByuType(SourceSearchVo svo);

    List<VideoSource> getZyplayers(QuerySourceVo vo);

    List<VideoSource> getListBywebAvai(QuerySourceVo vo);

    int updateSetJxName(VideoSource videoSource);

    int updateUnSetJxName(VideoSource videoSource);
    
    int updateSetYellowName(VideoSource videoSource);

    int updateUnSetYellowName(VideoSource videoSource);

}