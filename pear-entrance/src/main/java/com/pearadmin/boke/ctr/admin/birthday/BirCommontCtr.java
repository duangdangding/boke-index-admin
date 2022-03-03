package com.pearadmin.boke.ctr.admin.birthday;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lsh.birthday.Comment;
import com.pearadmin.birthday.service.CommentService;
import com.pearadmin.boke.utils.contains.BaseCtr;
import com.pearadmin.boke.vo.query.PageVo;

@RequestMapping("admin/birthday/commont")
@RestController
public class BirCommontCtr extends BaseCtr {
    
    @Autowired
    private CommentService commentService;
    
    @RequestMapping("/page")
    public Map<String,Object> getCommontPage(PageVo vo) {
        IPage<Comment> page = commentService.getPage(vo);
        return convertLayuiPage(page);
    }
}
