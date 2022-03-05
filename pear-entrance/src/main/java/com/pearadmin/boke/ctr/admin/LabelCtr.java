package com.pearadmin.boke.ctr.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pearadmin.boke.entry.Labels;
import com.pearadmin.boke.service.LabelsService;
import com.pearadmin.boke.utils.RedisUtil;
import com.pearadmin.boke.utils.contains.BaseCtr;
import com.pearadmin.boke.utils.contains.Constants;
import com.pearadmin.boke.vo.BootStrapResult;
import com.pearadmin.boke.vo.ResultDto;
import com.pearadmin.boke.vo.ResultDtoManager;
import com.pearadmin.boke.vo.query.QueryLabelVo;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("admin/label")
public class LabelCtr extends BaseCtr {
    
    @Autowired
    private LabelsService labelsService;
    
    @Autowired
    private RedisUtil redisUtil;

    /**
     * Describe: 获取标签列表视图
     * Param ModelAndView
     * Return 标签列表视图
     */
    @GetMapping("main")
    @ApiOperation(value = "获取标签列表视图")
    @PreAuthorize("hasPermission('/admin/label/main','sys:label:main')")
    public ModelAndView commont() {
        return getView(ADMINPATHPRE + "label");
    }
    
    @RequestMapping("/page")
    @PreAuthorize("hasPermission('/admin/label/page','sys:label:page')")
    public Map<String,Object> labelpage(QueryLabelVo vo) {
        IPage<Labels> labelPage = labelsService.getLabelPage(vo);
        return convertLayuiPage(labelPage);
    }

    @RequestMapping("/deleteState")
    @PreAuthorize("hasPermission('/admin/label/deleteState','sys:label:deleteState')")
    public ResultDto deleteState(String ids) {
        int i = labelsService.setDeleteState(ids,0);
        return returnDto(i);
    }

    @RequestMapping("/addLabel")
    @PreAuthorize("hasPermission('/admin/label/addLabel','sys:label:addLabel')")
    public ResultDto<String> addLabels(Labels labels) {
        String labelName = labels.getLabelName();
        int length = StrUtil.length(labelName);
        if (length == 0 || length > Constants.BokeXZ.CATELABELEN) {
            return fail(outLen(Constants.BokeXZ.CATELABELEN));
        }
        if (labels.getLabelId() == null || labels.getLabelId() == 0) {
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("label_name",labelName);
            Labels one = labelsService.getOne(wrapper);
            if (one != null) {
                return ResultDtoManager.success("此项已存在！");
            }
        }
        boolean b = labelsService.saveOrUpdate(labels);
        if (b) {
            redisUtil.preDel(Constants.RedisKey.LABELLIST);
            redisUtil.del(Constants.RedisKey.LABELRANK);
        }
        return returnDto(b);
    }

    @RequestMapping("/delLabel")
    @PreAuthorize("hasPermission('/admin/label/delLabel','sys:label:delLabel')")
    public ResultDto<String> delLabels(Integer labelId) {
        boolean b = labelsService.removeById(labelId);
        if (b) {
            redisUtil.preDel(Constants.RedisKey.LABELLIST);
            redisUtil.del(Constants.RedisKey.LABELRANK,Constants.RedisKey.USELABELLIST);
        }
        return returnDto(b);
    }

    @RequestMapping("/getAllLabels")
    @PreAuthorize("hasPermission('/admin/label/getAllLabels','sys:label:getAllLabels')")
    public BootStrapResult<Labels> getAllCategorys() {
        List<Labels> list = labelsService.list();
        return new BootStrapResult<Labels>(list,0L);
    }

}
