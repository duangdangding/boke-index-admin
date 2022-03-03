package com.pearadmin.boke.ctr.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pearadmin.boke.service.UsersService;
import com.pearadmin.boke.entry.Users;
import com.pearadmin.boke.utils.contains.BaseCtr;
import com.pearadmin.boke.vo.ResultDto;
import com.pearadmin.boke.vo.query.QueryUserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("admin/user")
public class UserCtr extends BaseCtr {
    
    @Autowired
    private UsersService usersService;

    @RequestMapping("/page")
    public Map<String,Object> userPage(QueryUserVo vo) {
        IPage<Users> page = usersService.getPage(vo);
        return convertLayuiPage(page);
    }

    @RequestMapping("/deleteState")
    public ResultDto deleteState(String ids,Integer useState) {
        int i = usersService.setDeleteState(ids,useState);
        return returnDto(i);
    }
    
    @RequestMapping("/resetPwd")
    public ResultDto resetPwd(String ids) {
        int i = usersService.resetPwd(ids);
        return returnDto(i);
    }
}
