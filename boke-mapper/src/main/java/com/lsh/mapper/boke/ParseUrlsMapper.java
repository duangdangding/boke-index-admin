package com.lsh.mapper.boke;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pearadmin.boke.entry.ParseUrls;
import com.pearadmin.boke.vo.query.QuerySourceVo;

/**
* ParseUrls数据接口层
*
* @author lushao
* @version 1.0.0 2021-07-03 11:13:41
*/
public interface ParseUrlsMapper extends BaseMapper<ParseUrls> {
    
    List<Map<String,Object>> getListByAvai();
    List<ParseUrls> getListByPage(Page page,@Param("vo") QuerySourceVo vo);
    
    @Update("update parse_urls set parse_active = 0 where parse_id = #{parseId}")
    int setNoActive(Integer parseId);
    
}