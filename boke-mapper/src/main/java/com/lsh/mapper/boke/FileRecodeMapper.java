package com.lsh.mapper.boke;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pearadmin.boke.entry.FileRecode;
import com.pearadmin.boke.vo.query.QueryFileRecodeVo;

public interface FileRecodeMapper extends BaseMapper<FileRecode> {

    IPage<FileRecode> getRecodePage(Page page,@Param("vo") QueryFileRecodeVo vo);
}
