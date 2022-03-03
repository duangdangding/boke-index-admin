package com.pearadmin.boke.ctr.user;

import com.pearadmin.boke.service.LabelsService;
import com.pearadmin.boke.service.UserLabelService;
import com.pearadmin.boke.entry.Labels;
import com.pearadmin.boke.entry.UserLabel;
import com.pearadmin.boke.utils.RedisUtil;
import com.pearadmin.boke.utils.TokenUtil;
import com.pearadmin.boke.utils.contains.BaseCtr;
import com.pearadmin.boke.utils.contains.Constants;
import com.pearadmin.boke.utils.contains.UserLoginToken;
import com.pearadmin.boke.vo.ResultDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hutool.core.util.StrUtil;

@RequestMapping("/label")
@RestController
public class LabelsCtr extends BaseCtr {
    
    @Autowired
    private LabelsService labelsService;
    
    @Autowired
    private UserLabelService userLabelService;
    
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 新增博客页面标签
     * @param userId
     * @return
     */
    @RequestMapping("/get/{userId}")
    public ResultDto getLabelByUserId(@PathVariable("userId") Integer userId) {
        // return returnDto(labelsService.getByUserId(userId));
        return returnDto(labelsService.getUseLabels());
    }

    /**
     * 首页右侧标签栏
     * @return
     */
    @RequestMapping("/get/labelRank")
    public ResultDto labelRank() {
        return returnDto(labelsService.labelRank());
    }
    
    @UserLoginToken
    @RequestMapping("/add")
    public ResultDto addLabelByUserId(String labelName) {
        int length = StrUtil.length(labelName);
        if (length == 0 || length > Constants.BokeXZ.CATELABELEN) {
            return fail(outLen(Constants.BokeXZ.CATELABELEN));
        }
        // 先查询总库有没有
        Integer byLabelName = labelsService.getByLabelName(labelName);
        if (byLabelName == null) {
            Labels labels = new Labels();
            labels.setLabelName(labelName);
            // labels.setDescription(labelName);
            labelsService.save(labels);
            redisUtil.del(Constants.RedisKey.LABELRANK);
            redisUtil.del(Constants.RedisKey.USELABELLIST);
            byLabelName = labels.getLabelId();
        }
        // 再去查询用户有没有关联
        /*UserLabel byUserIdLid = userLabelService.getByUserIdLid(byLabelName, TokenUtil.USERID);
        if (byUserIdLid == null) {
            byUserIdLid = new UserLabel();
            byUserIdLid.setUserId(TokenUtil.USERID);
            byUserIdLid.setLabelId(byLabelName);
            boolean b = userLabelService.save(byUserIdLid);
            if (b) {
                redisUtil.del(Constants.RedisKey.LABELLIST + byUserIdLid.getUserId());
            }
        }*/
        return success(byLabelName);
    }
    
    @UserLoginToken
    @RequestMapping("/del/{labelId}")
    public ResultDto delByLabelIdUserId(@PathVariable("labelId") Integer labelId) {
        UserLabel byUserIdLid = userLabelService.getByUserIdLid(labelId, TokenUtil.USERID);
        if (byUserIdLid == null) {
            return fail(NOTEXSIT);
        } else {
            int labelCountByBoke = labelsService.getLabelCountByBoke(labelId);
            if (labelCountByBoke > 0) {
                return fail("无法删除，其他文章正在使用该标签~");
            }
            boolean b = userLabelService.removeById(byUserIdLid.getUbId());
            if (b) {
                redisUtil.del(Constants.RedisKey.LABELLIST + byUserIdLid.getUserId());
            }
            return returnDto(b);
        }
    }

    /**
     * 去标签列表页
     * @return
     */
    @RequestMapping("/labelPage")
    public ModelAndView toLabelPage() {
        List<Labels> useLabels = labelsService.getUseLabels();
        Map<String,Object> map = new HashMap<>();
        map.put("labels",useLabels);
        map.put("labelCount",useLabels.size());
        return getView("labels",map);
    }

}
