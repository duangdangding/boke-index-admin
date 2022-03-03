package com.pearadmin.boke.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pearadmin.boke.entry.Comments;
import com.pearadmin.boke.vo.query.QueryCommontVo;

public interface CommonService {

    IPage<Comments> getCommontPage(QueryCommontVo vo);
    
    int commontDel(String ids);

}
