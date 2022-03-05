package com.pearadmin.boke.ctr.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pearadmin.boke.entry.Categorys;
import com.pearadmin.boke.service.CategorysService;
import com.pearadmin.boke.utils.RedisUtil;
import com.pearadmin.boke.utils.contains.BaseCtr;
import com.pearadmin.boke.utils.contains.Constants;
import com.pearadmin.boke.vo.BootStrapResult;
import com.pearadmin.boke.vo.ResultDto;
import com.pearadmin.boke.vo.ResultDtoManager;
import com.pearadmin.common.tools.SecurityUtil;
import com.pearadmin.system.domain.SysUser;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;

@RestController
public class CategorysCtr extends BaseCtr {
    
    @Autowired
    private CategorysService categorysService;
    
    @Autowired
    private RedisUtil redisUtil;
    
    @RequestMapping("/getAllCates")
    public BootStrapResult<Categorys> getAllCategorys() {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("delete_state",1);
        String key = Constants.RedisKey.CATELIST;
        if (redisUtil.hasKey(key)) {
            return new BootStrapResult<>(JSONUtil.toList(redisUtil.get(key).toString(),Categorys.class),0L);
        }
        List<Categorys> list = categorysService.list(wrapper);
        redisUtil.set(key,JSONUtil.toJsonStr(list));
        return new BootStrapResult<>(list,0L);
    }
    
    @RequestMapping("/addCate")
    public ResultDto<String> addCategory(Categorys categorys) {
        SysUser sysUser = SecurityUtil.currentUser();
        if (sysUser == null) {
            return ResultDtoManager.fail(-1,LOGIN);
        }

        String cateName = categorys.getCateName();
        int length = StrUtil.length(cateName);
        if (length == 0 || length > Constants.BokeXZ.CATELABELEN) {
            return fail(outLen(Constants.BokeXZ.CATELABELEN));
        }
        QueryWrapper<Categorys> wrapper = new QueryWrapper();
        wrapper.eq("cate_name",cateName);
        Categorys one = categorysService.getOne(wrapper);
        Integer id;
        if (one == null) {
            boolean save = categorysService.save(categorys);
            if (save) {
                redisUtil.del(Constants.RedisKey.CATELIST,Constants.RedisKey.CATERANK);
               id = categorys.getCateId();
            } else {
                return fail();
            }
        } else {
            id = one.getCateId();
        }
        return success(id);
    }
    
    @RequestMapping("/delCate")
    public ResultDto<String> delCategory(Integer cateId) {
        boolean b = categorysService.removeById(cateId);
        if (b) {
            redisUtil.del(Constants.RedisKey.CATELIST,Constants.RedisKey.CATERANK);
        }
        return returnDto(b);
    }
}
