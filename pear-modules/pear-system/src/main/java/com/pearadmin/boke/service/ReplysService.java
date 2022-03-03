package com.pearadmin.boke.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pearadmin.boke.entry.Replys;
import com.pearadmin.boke.vo.query.QueryCommontVo;

public interface ReplysService extends IService<Replys> {
    
    IPage<Replys> getReplayPage(QueryCommontVo vo);
    
    int deleteByIds(String ids);
}
