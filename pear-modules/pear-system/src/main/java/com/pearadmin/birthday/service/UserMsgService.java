package com.pearadmin.birthday.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lsh.birthday.UserMsg;
import com.pearadmin.boke.vo.query.PageVo;

public interface UserMsgService extends IService<UserMsg> {
    
    IPage<UserMsg> getPage(PageVo vo);
}
