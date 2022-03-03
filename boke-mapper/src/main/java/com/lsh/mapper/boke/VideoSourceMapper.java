package com.lsh.mapper.boke;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pearadmin.boke.entry.VideoSource;
import com.pearadmin.boke.vo.query.QuerySourceVo;
import com.pearadmin.boke.vo.query.SourceSearchVo;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
* VideoSource数据接口层
*
* @author lushao
* @version 1.0.0 2021-06-30 19:28:16
*/
public interface VideoSourceMapper extends BaseMapper<VideoSource> {
    
    List<VideoSource> getZyplayers(QuerySourceVo vo);
    
    List<VideoSource> getByuType(SourceSearchVo svo);
    List<VideoSource> getZyplayers(VideoSource videoSource);
    
    List<VideoSource> getListBywebAvai(Page page,@Param("vo") QuerySourceVo vo);
    List<VideoSource> selectNullCate();
    
    Map<String,Object> countBycause();
    
    @Update("update video_source set category = #{category} where source_id = #{sourceId}")
    int updateSetVideoCategory(VideoSource videoSource);
    
    @Update("update video_source set source_type = #{sourceType} where source_id = #{sourceId}")
    int updateSetVideoType(VideoSource videoSource);
    
    @Update("update video_source set source_name = CONCAT('J_',source_name),need_parse = #{needParse} where source_id = #{sourceId}")
    int updateSetJxName(VideoSource videoSource);
    
    @Update("update video_source set source_name = REPLACE(source_name,'J_',''),need_parse = #{needParse} where source_id = #{sourceId}")
    int updateUnSetJxName(VideoSource videoSource);
    
    @Update("update video_source set source_name = CONCAT('H_',source_name),source_type = #{sourceType} where source_id = #{sourceId}")
    int updateSetYellowName(VideoSource videoSource);
    
    @Update("update video_source set source_name = REPLACE(source_name,'H_',''),source_type = #{sourceType} where source_id = #{sourceId}")
    int updateUnSetYellowName(VideoSource videoSource);
    
    @Update("update video_source set source_name = #{sourceName} where source_id = #{sourceId}")
    int updateSourceName(String sourceName,Integer sourceId);
}