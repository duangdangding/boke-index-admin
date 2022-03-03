package com.pearadmin.boke.ctr.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pearadmin.boke.service.YoulianService;
import com.pearadmin.boke.entry.FriendUrl;
import com.pearadmin.boke.utils.contains.BaseCtr;
import com.pearadmin.boke.vo.ResultDto;
import com.pearadmin.boke.vo.query.PageVo;

@RestController
@RequestMapping("admin/friend")
public class FriendCtr extends BaseCtr {
    
    @Autowired
    private YoulianService youlianService;
    
    @RequestMapping("/page")
    public Map<String,Object> friendPage(PageVo vo) {
        // IPage<FriendUrl> friendPage = youlianService.getFriendPage(vo);
        List<FriendUrl> list = youlianService.list();
        return convertLayuiPage(list);
    }
    
    @RequestMapping("/updateOrAdd")
    public ResultDto updateOrAdd(FriendUrl friendUrl) {
        boolean b = youlianService.saveOrUpdate(friendUrl);
        return returnDto(b);
    }
    @RequestMapping("/del")
    public ResultDto youlianDel(Long id) {
        boolean b = youlianService.removeById(id);
        return returnDto(b);
    }
    
}
