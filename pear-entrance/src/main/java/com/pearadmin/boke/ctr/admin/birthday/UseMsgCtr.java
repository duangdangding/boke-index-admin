package com.pearadmin.boke.ctr.admin.birthday;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lsh.birthday.UserMsg;
import com.pearadmin.birthday.service.UserMsgService;
import com.pearadmin.boke.utils.contains.BaseCtr;
import com.pearadmin.boke.vo.query.PageVo;

@RestController
@RequestMapping("admin/birthday/usemsg")
public class UseMsgCtr extends BaseCtr {
    
    @Autowired
    private UserMsgService userMsgService;
    
    @RequestMapping("/page")
    public Map<String,Object> getPage(PageVo vo) {
        IPage<UserMsg> page = userMsgService.getPage(vo);
        return convertLayuiPage(page);
    }
    
}
