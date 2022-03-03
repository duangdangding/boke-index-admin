package com.pearadmin.boke.ctr.user;

import com.pearadmin.boke.utils.ApiUtil;
import com.pearadmin.boke.utils.contains.BaseCtr;
import com.pearadmin.boke.vo.ResultDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lushao
 * @Description 请求API控制器
 * @Date 2021/7/16 19:05
 * @Version 1.0
 */
@RestController
@RequestMapping("/api")
public class RequestCtr extends BaseCtr {
    
    @RequestMapping("/getJson")
    public String getJson(String url) {
        return ApiUtil.httpRequest(url, null);
    }
    public ResultDto getJson_bak(String url) {
        String s = ApiUtil.httpRequest(url, null);
        return returnDto(s);
    }
}
