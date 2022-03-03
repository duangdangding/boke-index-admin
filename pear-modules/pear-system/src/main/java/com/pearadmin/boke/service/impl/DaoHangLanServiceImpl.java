package com.pearadmin.boke.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pearadmin.boke.entry.Navigation;
import com.pearadmin.boke.entry.UserCustom;
import com.lsh.mapper.boke.UserCustomMapper;
import com.lsh.mapper.boke.DaoHangLanMapper;
import com.pearadmin.boke.service.DaoHangLanService;
import com.pearadmin.boke.utils.RedisUtil;
import com.pearadmin.boke.utils.contains.Constants;
import com.pearadmin.boke.vo.NavAndIcon;
import com.pearadmin.boke.vo.NavDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;

/**
 * @Author ycf
 * @Description $
 * @Date 17:36
 * @Version 1.0
 */
@Service
public class DaoHangLanServiceImpl extends ServiceImpl<DaoHangLanMapper, Navigation> implements DaoHangLanService {

    @Autowired
    private DaoHangLanMapper daoHangLanMapper;

    @Autowired
    private UserCustomMapper userCustomMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public List<NavDto> getNavList() {
        QueryWrapper<Navigation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("nav_show",0);
        queryWrapper.orderByAsc("order_num");
        List<Navigation> navigations = daoHangLanMapper.selectList(queryWrapper);
        return convert(navigations);
    }

    @Override
    public NavAndIcon getInfo(Integer userId) {
        List<NavDto> navList = getNavList();
        NavAndIcon navAndIcon = new NavAndIcon();
        navAndIcon.setCustom(navList);
        String key = Constants.RedisKey.NAVIGATION + userId;
        UserCustom userCustom;
        if (redisUtil.hasKey(key)) {
            userCustom = JSONUtil.toBean(redisUtil.get(key).toString(),UserCustom.class);
        } else {
            QueryWrapper<UserCustom> queryWrapper = new QueryWrapper();
            queryWrapper.in("user_id",userId,0);
            queryWrapper.orderByDesc("user_id");
            userCustom = userCustomMapper.selectList(queryWrapper).get(0);
            redisUtil.set(key,JSONUtil.toJsonStr(userCustom));
        }
        navAndIcon.setCuteicon(userCustom.getCuteicon().split(","));
        navAndIcon.setIsGratuity(userCustom.getGratuityIs() == 0);
        navAndIcon.setGratuity(userCustom.getGratuity());
        navAndIcon.setIsCuteicon(userCustom.getCuteiconIs() == 0);
        return navAndIcon;
    }

    @Override
    public boolean updateNavTitle(String navigation) {
        if (StrUtil.isNotEmpty(navigation)) {
            return redisUtil.set(Constants.RedisKey.SUBTITLE, navigation);
        }
        return false;
    }

    protected List<NavDto> convert(List<Navigation> navigations) {
        List<NavDto> dtos = new ArrayList<>();
        navigations.forEach(nav -> {
            NavDto dto = new NavDto();
            dto.setName(nav.getNavTitle());
            dto.setLink(nav.getNavUrl());
            dto.setIstarget(nav.getTarget());
            dto.setId_str(nav.getIdStr());
            dto.setHtml_str(nav.getHtmlStr());
            dtos.add(dto);
        });
        return dtos;
    }
}
