package com.pearadmin.boke.ctr.admin;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pearadmin.boke.entry.FrantPage;
import com.pearadmin.boke.service.FrantPageService;
import com.pearadmin.boke.utils.contains.BaseCtr;
import com.pearadmin.boke.vo.ResultDto;

/**
* FrantPage控制层
*
* @author lushao
* @version 1.0.0 2022-01-17 13:54:52
*/
@RestController
@RequestMapping("/admin/frant")
public class AdminFrantPageCtr extends BaseCtr {

    @Resource
    private FrantPageService frantPageService;

    @RequestMapping("/list")
    @PreAuthorize("hasPermission('/admin/frant/list','sys:frant:list')")
    public Map<String,Object> list(FrantPage vo) {
        IPage<FrantPage> classifies = frantPageService.allList(vo);
        return convertLayuiPage(classifies);
    }

    @RequestMapping("/addOrEdit")
    @PreAuthorize("hasPermission('/admin/frant/addOrEdit','sys:frant:addOrEdit')")
    public ResultDto addOrEdit(FrantPage frantPage) {
        boolean b = frantPageService.saveOrUpdate(frantPage);
        return returnDto(b);
    }

    @RequestMapping("/deleteState")
    @PreAuthorize("hasPermission('/admin/frant/deleteState','sys:frant:deleteState')")
    public ResultDto deleteState(Integer id,int deleteState) {
        UpdateWrapper<FrantPage> wrapper = new UpdateWrapper<>();
        wrapper.set("delete_state",deleteState);
        wrapper.eq("frant_id",id);
        boolean update = frantPageService.update(wrapper);
        return returnDto(update);
    }

    @RequestMapping("/delete")
    @PreAuthorize("hasPermission('/admin/frant/delete','sys:frant:delete')")
    public ResultDto delete(@RequestParam("ids") List<Long> ids) {
        boolean b = frantPageService.removeByIds(ids);
        return returnDto(b);
    }
}