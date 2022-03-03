package com.pearadmin.boke.ctr.admin;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pearadmin.boke.service.CommonService;
import com.pearadmin.boke.service.ReplysService;
import com.pearadmin.boke.entry.Comments;
import com.pearadmin.boke.entry.Replys;
import com.pearadmin.boke.utils.contains.BaseCtr;
import com.pearadmin.boke.vo.ResultDto;
import com.pearadmin.boke.vo.query.QueryCommontVo;

@RestController
@RequestMapping("admin/commontReplay")
public class CommontReplayCtr extends BaseCtr {
    
    @Autowired
    private CommonService commonService;
    
    @Autowired
    private ReplysService replysService;
    
    @RequestMapping("/commontPage")
    public Map<String,Object> getcommontPage(QueryCommontVo vo) {
        IPage<Comments> commontPage = commonService.getCommontPage(vo);
        return convertLayuiPage(commontPage);
    }
    
    @RequestMapping("/replayPage")
    public Map<String,Object> getreplayPage(QueryCommontVo vo) {
        IPage<Replys> replayPage = replysService.getReplayPage(vo);
        return convertLayuiPage(replayPage);
    }
    
    @RequestMapping("/commontDel")
    public ResultDto commontDel(String ids) {
        int i = commonService.commontDel(ids);
        return returnDto(i);
    }
    
    @RequestMapping("/replayDel")
    public ResultDto replayDel(String ids) {
        int i = replysService.deleteByIds(ids);
        return returnDto(i);
    }
}
