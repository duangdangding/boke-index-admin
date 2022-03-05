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
import com.pearadmin.boke.entry.Categorys;
import com.pearadmin.boke.service.CategorysService;
import com.pearadmin.boke.utils.RedisUtil;
import com.pearadmin.boke.utils.contains.BaseCtr;
import com.pearadmin.boke.utils.contains.Constants;
import com.pearadmin.boke.vo.ResultDto;
import com.pearadmin.boke.vo.query.QueryTypeVo;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("admin/bokeType")
public class BokeTypeCtr extends BaseCtr {
    
    @Autowired
    private CategorysService categorysService;
    
    @Autowired
    private RedisUtil redisUtil;

    /**
     * Describe: 获取博客类型列表视图
     * Param ModelAndView
     * Return 博客类型列表视图
     */
    @GetMapping("page")
    @ApiOperation(value = "获取博客类型列表视图")
    @PreAuthorize("hasPermission('/admin/bokeType/page','sys:bokeType:main')")
    public ModelAndView main() {
        return getView(ADMINPATHPRE + "bokeType");
    }

    @RequestMapping("/list")
    @PreAuthorize("hasPermission('/admin/bokeType/list','sys:bokeType:list')")
    public Map<String,Object> typepage(QueryTypeVo vo) {
        IPage<Categorys> labelPage = categorysService.getTypePage(vo);
        return convertLayuiPage(labelPage);
    }

    @RequestMapping("/deleteState")
    @PreAuthorize("hasPermission('/admin/bokeType/deleteState','sys:bokeType:deleteState')")
    public ResultDto deleteState(@RequestParam("ids") List<Long> ids) {
        int i = categorysService.setDeleteState(ids,0);
        if (i > 0) {
            redisUtil.del(Constants.RedisKey.CATELIST);
        }
        return returnDto(i);
    }
    @RequestMapping("/forever")
    @PreAuthorize("hasPermission('/admin/bokeType/forever','sys:bokeType:forever')")
    public ResultDto forever(@RequestParam("ids") List<Long> ids) {
        boolean i = categorysService.removeByIds(ids);
        if (i) {
            redisUtil.del(Constants.RedisKey.CATELIST);
        }
        return returnDto(i);
    }
    @RequestMapping("/update")
    @PreAuthorize("hasPermission('/admin/bokeType/update','sys:bokeType:update')")
    public ResultDto update(Categorys categorys) {
        boolean b = categorysService.updateById(categorys);
        return returnDto(b);
    }
    
}
