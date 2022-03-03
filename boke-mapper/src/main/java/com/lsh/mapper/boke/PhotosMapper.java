package com.lsh.mapper.boke;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pearadmin.boke.entry.Photos;

public interface PhotosMapper extends BaseMapper<Photos> {
    
    @Update("update photos set delete_state = 0 where photo_url = #{url}")
    int setDeleteStateByUrl(String url);

    @Select("select * from photos where photo_url = #{url} and user_id = #{userId}")
    Photos getByUrlAndUser(String url,Integer userId);
    
}
