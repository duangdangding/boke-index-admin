package com.pearadmin.boke.utils;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pearadmin.boke.entry.Visits;
import com.pearadmin.boke.service.VisitsService;
import com.pearadmin.boke.utils.contains.Constants;
import com.pearadmin.boke.utils.ip.IPHelper;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VisitsUtil {

    /**
     * 记录访问数据
     * @param request
     * @param visitsService
     * @param redisUtil
     */
    public static void setVisitCount(HttpServletRequest request, VisitsService visitsService, RedisUtil redisUtil) {
        try {
            String userAgent = request.getHeader("user-agent");
            String ip = IPHelper.getIp(request);
            QueryWrapper<Visits> wrapper = new QueryWrapper<>();
            wrapper.eq("visit_ip",ip);

            Visits one = visitsService.getOne(wrapper);
            // 如果是第一次则查询 ip地址 客户端系统
            if (one == null) {
                Visits visits = new Visits();
                if (TokenUtil.USERID != null) {
                    visits.setUserId(TokenUtil.USERID);
                }
                visits.setVisitIp(ip);
                String client = MyStringUtil.getClient(userAgent);
                visits.setVisitClient(client);
                visits.setUserAgent(userAgent);
                visits.setVisitCount(1);
                String ipAddrByIp = IPHelper.getIpAddrByIp(ip);
                JSONObject jsonObject = JSONUtil.parseObj(ipAddrByIp);
                JSONObject data = JSONUtil.parseObj(jsonObject.get("data"));
                String country = (String) data.get("country");
                String city = (String) data.get("province");
                visits.setCountry(country);
                visits.setCity(city);
                visits.setIpAddr(ipAddrByIp);
                boolean save = visitsService.save(visits);
            } else {
//                第二次只保存次数
                Visits visits = new Visits();
                visits.setVisitId(one.getVisitId());
                if (TokenUtil.USERID != null) {
                    visits.setUserId(TokenUtil.USERID);
                }
                visits.setVisitCount(one.getVisitCount() + 1);
                boolean b = visitsService.updateById(visits);
            }

//        保存今日访问量
            String day = Constants.RedisKey.DAYVISIT + Constants.DateFormat.LDATE.format(new Date());
            if (redisUtil.hasKey(day)) {
                redisUtil.incr(day,1);
            } else {
                redisUtil.set(day,1);
            }
//总访问量
            if (redisUtil.hasKey(Constants.RedisKey.VISITCOUNT)) {
                redisUtil.incr(Constants.RedisKey.VISITCOUNT,1);
            } else {
                redisUtil.set(Constants.RedisKey.VISITCOUNT,1);
            }
        } catch (Exception e) {
            log.error("setVisitCount error:->>>>" + e.getMessage());
        }
    }
}
