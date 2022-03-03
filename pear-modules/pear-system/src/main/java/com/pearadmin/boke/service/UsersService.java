package com.pearadmin.boke.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pearadmin.boke.entry.Users;
import com.pearadmin.boke.vo.query.QueryUserVo;

public interface UsersService extends IService<Users> {

    int setDeleteState(String ids,Integer deleteState);
    int resetPwd(String ids);
    
    Users getUserByName(String username);
    Users getUserByEmail(String email);
    
    Users getUserByNEP(Users users);

    Users getByUserId(int userId);

    int setFace(Users users);

    int setUrls(String column,String url,Integer userId);

    int setsignature(String signature,Integer userId);
    
    int unOrbdMail(Integer userId,int bd);
    
    int unOrbdPhone(Integer userId,int bd);

    int ghMail(String email,Integer userId);

    int ghPhone(String phone,Integer userId);
    
    int updatePWD(String pwd,Integer userId);
    
    Long getByUserIdCount(Integer userId);
    
    IPage<Users> getPage(QueryUserVo vo);
}
