package com.lsh.mapper.boke;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pearadmin.boke.entry.Users;
import com.pearadmin.boke.vo.query.QueryUserVo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface UsersMapper extends BaseMapper<Users> {

    Users getUserByNEP(Users users);
    
    List<Users> getByUserId(int userId);
    
    int setFace(Users users);

    int setUrls(String column,String url,Integer userId);
    
    int setsignature(String signature,Integer userId);
    
    int unOrbdMail(int bd,Integer userId);
    
    int unOrbdPhone(int bd,Integer userId);
    
    int ghMail(String email,Integer userId);
    
    int ghPhone(String phone,Integer userId);

    int updatePWD(String pwd,Integer userId);

    IPage<Users> getPage(Page page,@Param("vo") QueryUserVo vo);
}
