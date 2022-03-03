package com.pearadmin.boke.ctr.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pearadmin.boke.service.BokesService;
import com.pearadmin.boke.utils.RedisUtil;
import com.pearadmin.boke.utils.contains.BaseCtr;
import com.pearadmin.boke.utils.contains.Constants;
import com.pearadmin.boke.vo.BokeListEntry;
import com.pearadmin.boke.vo.query.QueryBokeVo;
import com.pearadmin.boke.vo.ResultDto;

@RestController
@RequestMapping("admin/boke")
public class BokeCtr extends BaseCtr {
    
    @Autowired
    private BokesService bokesService;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 分页查询博客列表
     * @param vo
     * @return
     */
    @RequestMapping("/mbpage")
    public Map<String, Object> mbPage(QueryBokeVo vo) {
        IPage<BokeListEntry> bokes1 = bokesService.getBokesList(vo);
        return convertLayuiPage(bokes1);
    }
    
    @RequestMapping("/deleteState")
    public ResultDto deleteState(@RequestParam("ids") List<Long> ids) {
        int i = bokesService.setDeleteState(ids, 0);
        return returnDto(i);
    }
    @RequestMapping("/forever")
    public ResultDto forever(@RequestParam("ids") List<Long> ids) {
        boolean i = bokesService.removeByIds(ids);
        redisUtil.del(Constants.RedisKey.LOOKRANK);
        return returnDto(i);
    }
    
    @RequestMapping("/unPass")
    public ResultDto unPass(@RequestParam("ids") List<Long> ids) {
        int i = bokesService.setUnPass(ids, 2);
        return returnDto(i);
    }
    
}
