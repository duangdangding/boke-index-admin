package com.pearadmin.boke.ctr.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pearadmin.boke.service.CategorysService;
import com.pearadmin.boke.entry.Categorys;
import com.pearadmin.boke.utils.RedisUtil;
import com.pearadmin.boke.utils.contains.BaseCtr;
import com.pearadmin.boke.utils.contains.Constants;
import com.pearadmin.boke.vo.ResultDto;
import com.pearadmin.boke.vo.query.QueryTypeVo;

@RestController
@RequestMapping("admin/bokeType")
public class BokeTypeCtr extends BaseCtr {
    
    @Autowired
    private CategorysService categorysService;
    
    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping("/page")
    public Map<String,Object> typepage(QueryTypeVo vo) {
        IPage<Categorys> labelPage = categorysService.getTypePage(vo);
        return convertLayuiPage(labelPage);
    }

    @RequestMapping("/deleteState")
    public ResultDto deleteState(@RequestParam("ids") List<Long> ids) {
        int i = categorysService.setDeleteState(ids,0);
        if (i > 0) {
            redisUtil.del(Constants.RedisKey.CATELIST);
        }
        return returnDto(i);
    }
    @RequestMapping("/forever")
    public ResultDto forever(@RequestParam("ids") List<Long> ids) {
        boolean i = categorysService.removeByIds(ids);
        if (i) {
            redisUtil.del(Constants.RedisKey.CATELIST);
        }
        return returnDto(i);
    }
    
}
