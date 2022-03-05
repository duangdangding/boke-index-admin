package com.pearadmin.boke.ctr.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pearadmin.boke.service.BokesService;
import com.pearadmin.boke.utils.RedisUtil;
import com.pearadmin.boke.utils.contains.BaseCtr;
import com.pearadmin.boke.utils.contains.Constants;
import com.pearadmin.boke.vo.BokeListEntry;
import com.pearadmin.boke.vo.ResultDto;
import com.pearadmin.boke.vo.query.QueryBokeVo;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("admin/boke")
public class BokeCtr extends BaseCtr {
    
    @Autowired
    private BokesService bokesService;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * Describe: 获取博客列表视图
     * Param ModelAndView
     * Return 博客列表视图
     */
    @GetMapping("page")
    @ApiOperation(value = "获取博客文章列表视图")
    @PreAuthorize("hasPermission('/admin/boke/page','sys:boke:main')")
    public ModelAndView main() {
        return getView(ADMINPATHPRE + "boke");
    }

    /**
     * 分页查询博客列表
     * @param vo
     * @return
     */
    @RequestMapping("/mbpage")
    @PreAuthorize("hasPermission('/admin/boke/mbpage','sys:boke:mbpage')")
    public Map<String, Object> mbPage(QueryBokeVo vo) {
        IPage<BokeListEntry> bokes1 = bokesService.getBokesList(vo);
        return convertLayuiPage(bokes1);
    }
    
    @RequestMapping("/deleteState")
    @PreAuthorize("hasPermission('/admin/boke/deleteState','sys:boke:deleteState')")
    public ResultDto deleteState(@RequestParam("ids") List<Long> ids) {
        int i = bokesService.setDeleteState(ids, 0);
        return returnDto(i);
    }
    @RequestMapping("/forever")
    @PreAuthorize("hasPermission('/admin/boke/forever','sys:boke:forever')")
    public ResultDto forever(@RequestParam("ids") List<Long> ids) {
        boolean i = bokesService.removeByIds(ids);
        redisUtil.del(Constants.RedisKey.LOOKRANK);
        return returnDto(i);
    }
    
    @RequestMapping("/unPass")
    @PreAuthorize("hasPermission('/admin/boke/unPass','sys:boke:unPass')")
    public ResultDto unPass(@RequestParam("ids") List<Long> ids) {
        int i = bokesService.setUnPass(ids, 2);
        return returnDto(i);
    }
    
}
