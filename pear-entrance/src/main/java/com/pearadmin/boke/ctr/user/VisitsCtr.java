package com.pearadmin.boke.ctr.user;

import com.pearadmin.boke.service.VisitsService;
import com.pearadmin.boke.utils.RedisUtil;
import com.pearadmin.boke.utils.contains.BaseCtr;
import com.pearadmin.boke.utils.contains.Constants;
import com.pearadmin.boke.vo.ResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class VisitsCtr extends BaseCtr {
    
    @Autowired
    private VisitsService visitsService;
    
    @Autowired
    private RedisUtil redisUtil;
    
    @RequestMapping("/web/count")
    public ResultDto<Map<String,Object>> getWebCount() {
        Map<String,Object> map = new HashMap<>();
        try {
            Object all = redisUtil.get(Constants.RedisKey.VISITCOUNT);
            Object day = redisUtil.get(Constants.RedisKey.DAYVISIT + Constants.DateFormat.LDATE.format(new Date()));
            map.put("webCount",all);
            map.put("dayCount",day);
        } catch (Exception e) {
            map.put("webCount",0);
            map.put("dayCount",0);
        }
        return success(map);
    }
}
