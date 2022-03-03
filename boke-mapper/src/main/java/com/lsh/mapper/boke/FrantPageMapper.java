package com.lsh.mapper.boke;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pearadmin.boke.entry.Classify;
import com.pearadmin.boke.entry.FrantPage;

/**
* FrantPage数据接口层
*
* @author lushao
* @version 1.0.0 2022-01-17 13:54:52
*/
public interface FrantPageMapper extends BaseMapper<FrantPage> {
    
    List<Classify> groupFrants();

    IPage<FrantPage> allList(Page<FrantPage> page,@Param("vo") FrantPage vo);
    
}