package com.lsh.mapper.boke;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pearadmin.boke.entry.Replys;

public interface ReplysMapper extends BaseMapper<Replys> {
    
    @Select("select reply_id from replys where comment_id = #{cId}")
    List<Integer> getIdsBycId(Integer cId);
}
