package com.pearadmin.boke.ctr.admin;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pearadmin.boke.entry.Comments;
import com.pearadmin.boke.entry.Replys;
import com.pearadmin.boke.service.CommonService;
import com.pearadmin.boke.service.ReplysService;
import com.pearadmin.boke.utils.contains.BaseCtr;
import com.pearadmin.boke.vo.ResultDto;
import com.pearadmin.boke.vo.query.QueryCommontVo;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("admin/commontReplay")
public class CommontReplayCtr extends BaseCtr {
    
    @Autowired
    private CommonService commonService;
    
    @Autowired
    private ReplysService replysService;

    /**
     * Describe: 获取留言列表视图
     * Param ModelAndView
     * Return 留言列表视图
     */
    @GetMapping("commontp")
    @ApiOperation(value = "获取留言列表视图")
    @PreAuthorize("hasPermission('/admin/commontReplay/commontp','sys:commontReplay:commontp')")
    public ModelAndView commont() {
        return getView(ADMINPATHPRE + "commont");
    }
    /**
     * Describe: 获取回复列表视图
     * Param ModelAndView
     * Return 回复列表视图
     */
    @GetMapping("replayp")
    @ApiOperation(value = "获取回复列表视图")
    @PreAuthorize("hasPermission('/admin/commontReplay/replayp','sys:commontReplay:replayp')")
    public ModelAndView replay() {
        return getView(ADMINPATHPRE + "replay");
    }
    
    @RequestMapping("/commontPage")
    @PreAuthorize("hasPermission('/admin/commontReplay/commontPage','sys:commontReplay:commontPage')")
    public Map<String,Object> getcommontPage(QueryCommontVo vo) {
        IPage<Comments> commontPage = commonService.getCommontPage(vo);
        return convertLayuiPage(commontPage);
    }
    
    @RequestMapping("/replayPage")
    @PreAuthorize("hasPermission('/admin/commontReplay/replayPage','sys:commontReplay:replayPage')")
    public Map<String,Object> getreplayPage(QueryCommontVo vo) {
        IPage<Replys> replayPage = replysService.getReplayPage(vo);
        return convertLayuiPage(replayPage);
    }
    
    @RequestMapping("/commontDel")
    @PreAuthorize("hasPermission('/admin/commontReplay/commontDel','sys:commontReplay:commontDel')")
    public ResultDto commontDel(String ids) {
        int i = commonService.commontDel(ids);
        return returnDto(i);
    }
    
    @RequestMapping("/replayDel")
    @PreAuthorize("hasPermission('/admin/commontReplay/replayDel','sys:commontReplay:replayDel')")
    public ResultDto replayDel(String ids) {
        int i = replysService.deleteByIds(ids);
        return returnDto(i);
    }
}
