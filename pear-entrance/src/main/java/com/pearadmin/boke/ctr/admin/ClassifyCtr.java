package com.pearadmin.boke.ctr.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.pearadmin.boke.entry.Classify;
import com.pearadmin.boke.service.ClassifyService;
import com.pearadmin.boke.utils.contains.BaseCtr;
import com.pearadmin.boke.vo.ResultDto;
import com.pearadmin.boke.vo.SelectVo;

import cn.hutool.core.collection.CollectionUtil;
import io.swagger.annotations.ApiOperation;

/**
* Classify控制层(导航分类)
*
* @author lushao
* @version 1.0.0 2022-01-17 13:54:52
*/
@RestController
@RequestMapping("/admin/classify")
public class ClassifyCtr extends BaseCtr {

    @Autowired
    private ClassifyService classifyService;

    /**
     * Describe: 获取导航分类列表视图
     * Param ModelAndView
     * Return 导航分类列表视图
     */
    @GetMapping("page")
    @ApiOperation(value = "获取导航分类列表视图")
    @PreAuthorize("hasPermission('/admin/classify/page','sys:classify:main')")
    public ModelAndView main() {
        return getView(ADMINPATHPRE + "frantPage");
    }
    
    @RequestMapping("/list")
    @PreAuthorize("hasPermission('/admin/classify/list','sys:classify:list')")
    public Map<String,Object> list() {
        QueryWrapper<Classify> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("sort");
        List<Classify> classifies = classifyService.list(wrapper);
        return convertLayuiPage(classifies);
    }

    @RequestMapping("/select")
    @PreAuthorize("hasPermission('/admin/classify/select','sys:classify:select')")
    public List<SelectVo> getClassifyList() {
        List<Classify> list = classifyService.useList();
        if (CollectionUtil.isNotEmpty(list)) {
            return convertSelectVo(list);
        }
        return null;
    }

    @RequestMapping("/addOrEdit")
    @PreAuthorize("hasPermission('/admin/classify/addOrEdit','sys:classify:addOrEdit')")
    public ResultDto addOrEdit(Classify classify) {
        boolean b = classifyService.saveOrUpdate(classify);
        return returnDto(b);
    }

    @RequestMapping("/deleteState")
    @PreAuthorize("hasPermission('/admin/classify/deleteState','sys:classify:deleteState')")
    public ResultDto deleteState(Integer id,int deleteState) {
        UpdateWrapper<Classify> wrapper = new UpdateWrapper<>();
        wrapper.set("delete_state",deleteState);
        wrapper.eq("id",id);
        boolean update = classifyService.update(wrapper);
        return returnDto(update);
    }

    @RequestMapping("/delete")
    @PreAuthorize("hasPermission('/admin/classify/delete','sys:classify:delete')")
    public ResultDto delete(@RequestParam("ids") List<Long> ids) {
        boolean b = classifyService.removeByIds(ids);
        return returnDto(b);
    }


    protected List<SelectVo> convertSelectVo(List<Classify> list) {
        List<SelectVo> selectVos = new ArrayList<>();
        list.forEach(e -> {
            selectVos.add(new SelectVo(e.getId(),e.getTitle()));
        });
        return selectVos;
    }
}