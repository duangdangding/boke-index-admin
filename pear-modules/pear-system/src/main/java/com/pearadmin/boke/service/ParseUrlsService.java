package com.pearadmin.boke.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pearadmin.boke.entry.ParseUrls;
import com.pearadmin.boke.vo.query.QuerySourceVo;

/**
* ParseUrls接口层
*
* @author lushao
* @version 1.0.0 2021-07-03 11:13:41
*/
public interface ParseUrlsService extends IService<ParseUrls> {

    List<ParseUrls> getByApi(String api);
    
    IPage<ParseUrls> getParsePage(QuerySourceVo vo);

    int setShoudong(String ids);
    int setDeleteState(String ids);
}