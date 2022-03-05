package com.pearadmin.boke.ctr.admin.birthday;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lsh.birthday.Comment;
import com.pearadmin.birthday.service.CommentService;
import com.pearadmin.boke.utils.contains.BaseCtr;
import com.pearadmin.boke.vo.query.PageVo;

import io.swagger.annotations.ApiOperation;

@RequestMapping("admin/birthday/commont")
@RestController
public class BirCommontCtr extends BaseCtr {
    
    @Autowired
    private CommentService commentService;

    /**
     * Describe: 获取生日留言列表视图
     * Param ModelAndView
     * Return 生日留言列表视图
     */
    @GetMapping("main")
    @ApiOperation(value = "获取生日留言列表视图")
    @PreAuthorize("hasPermission('/admin/birthday/commont/main','sys:birthday:main')")
    public ModelAndView main() {
        return getView(ADMINPATHPRE + "birthday/commont");
    }
    
    @RequestMapping("/page")
    @PreAuthorize("hasPermission('/admin/birthday/commont/page','sys:birthday:page')")
    public Map<String,Object> getCommontPage(PageVo vo) {
        IPage<Comment> page = commentService.getPage(vo);
        return convertLayuiPage(page);
    }
}
