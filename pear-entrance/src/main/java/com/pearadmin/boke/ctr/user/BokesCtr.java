package com.pearadmin.boke.ctr.user;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pearadmin.boke.entry.Bokes;
import com.pearadmin.boke.entry.CategoryCount;
import com.pearadmin.boke.entry.Comments;
import com.pearadmin.boke.entry.CreateDates;
import com.pearadmin.boke.entry.Lookups;
import com.pearadmin.boke.service.BokesService;
import com.pearadmin.boke.service.CommentsService;
import com.pearadmin.boke.service.DateArchiveService;
import com.pearadmin.boke.service.VisitsService;
import com.pearadmin.boke.utils.MyStringUtil;
import com.pearadmin.boke.utils.RedisUtil;
import com.pearadmin.boke.utils.VisitsUtil;
import com.pearadmin.boke.utils.contains.BaseCtr;
import com.pearadmin.boke.utils.contains.BookType;
import com.pearadmin.boke.utils.contains.Constants;
import com.pearadmin.boke.utils.ip.IPHelper;
import com.pearadmin.boke.vo.BokeListEntry;
import com.pearadmin.boke.vo.ResultDto;
import com.pearadmin.boke.vo.ResultDtoManager;
import com.pearadmin.common.tools.SecurityUtil;
import com.pearadmin.system.domain.SysUser;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class BokesCtr extends BaseCtr {
    // Logger log = LoggerFactory.getLogger(BokesCtr.class);

    @Autowired
    private BokesService bokesService;
    
    @Autowired
    private DateArchiveService dateArchiveService;

    @Autowired
    private CommentsService commentsService;

    @Autowired
    private VisitsService visitsService;

    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping({"/", "/index"})
    public ModelAndView getBokes_thy(Integer pageNumber, Bokes bokes, String navTitle, HttpServletRequest request) {
//        获取请求详细信息
        /*Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames.hasMoreElements()) {
            System.out.println(headerNames.nextElement());
        }*/
        VisitsUtil.setVisitCount(request, visitsService, redisUtil);
        Map<String, Object> map = indexParam(pageNumber, bokes, navTitle);
        String requestURL = request.getRequestURL().toString();
        String ip = IPHelper.getIp(request);
        String url = requestURL.substring(0, requestURL.lastIndexOf("/"));
        if (url.contains("localhost")) {
            url = url.replace("localhost",ip);
        }
        // map.put("b_host", requestURL.substring(0, requestURL.lastIndexOf("/")));
        map.put("b_host", url);
        return getView("boke/index", map);
    }
    
    /**
     * 局部刷新
     *
     * @param pageNumber
     * @param bokes
     * @param navTitle
     * @return
     */
    @RequestMapping("/page")
    public ModelAndView getBokes_thy2(Integer pageNumber, Bokes bokes, String navTitle) {
        Map<String, Object> map = indexParam(pageNumber, bokes, navTitle);
        return getView("boke/index::content_refresh", map);
    }

    private Map<String, Object> indexParam(Integer pageNumber, Bokes bokes, String navTitle) {
        if (ObjectUtil.isEmpty(pageNumber) || pageNumber <= 0) {
            pageNumber = 1;
        }
        SysUser sysUser = SecurityUtil.currentUser();
        if (sysUser != null) {
            bokes.setUserId(sysUser.getUserId());
        }
        IPage<BokeListEntry> bokes1 = bokesService.getBokes(pageNumber, Constants.PageSize.SIZE10, bokes);
        Map<String, Object> param = new HashMap<>(2);
        param.put("bokes", bokes1);
        if (!StrUtil.isBlank(navTitle)) {
            navTitle = ">>>" + navTitle;
            param.put("navTitle", navTitle);
            Integer cateId = bokes.getCateId();
            Integer lId = bokes.getLId();
            String title = bokes.getTitle();
            String createDate = bokes.getCreateDate();
            String condition = "";
            if (cateId != null) {
                condition = "cateId=" + cateId;
            }
            if (lId != null) {
                condition = "lId=" + lId;
            }
            if (!StrUtil.isBlank(title)) {
                condition = "title=" + title;
            }
            if (!StrUtil.isBlank(createDate)) {
                condition = "createDate=" + createDate;
            }
            param.put("navParam", condition + "&");
        }
        Long userId = bokes.getUserId();
        if (userId != null) {
            param.put("navUserid", userId + "&");
        }
        return param;
    }

    @RequestMapping("/bokexq/{bokeId}")
    public ModelAndView getBoke(@PathVariable("bokeId") String bokeId, HttpServletRequest request) {
        Integer id;
        try {
            id = Integer.valueOf(bokeId);
        } catch (NumberFormatException e) {
            return to400View();
        }

        BokeListEntry boke = bokesService.getBokesById(id, null);
        if (boke == null || boke.getDeleteState() == 0) {
            return to400View();
        }

        int i = bokesService.setLookUp(id);
        Map<String, Object> model = new HashMap<>();
        String labelId = boke.getLabelId();
        if (StrUtil.isNotEmpty(labelId) && !"0".equals(labelId)) {
            String labelNames = boke.getLabelNames();
            boke.setLabelIds(labelId.split(","));
            boke.setLabNames(labelNames.split(","));
        }
        model.put("boke", boke);
        model.put("bokeId", boke.getBokeId());
        List<Comments> comments = commentsService.getCommentsByBokeId(id);
        // IPage<Comments> comments = commentsService.getCommentsByBidAndPage(id, 1);
        model.put("comments", comments);
        // 设置浏览量
        VisitsUtil.setVisitCount(request, visitsService, redisUtil);
        return getView("boke/boke", model);
    }

    @RequestMapping("/t/editor/{bokeId}")
    // @PreAuthorize("hasPermission('/t/boke/editor','sys:boke:editor')")
    public ModelAndView gotoEditor(@PathVariable("bokeId") Integer bokeId) {
        SysUser sysUser = SecurityUtil.currentUser();
        if (sysUser == null) {
            return toLoginView();
        }
        BokeListEntry boke = bokesService.getBokesByEmId(bokeId, sysUser.getUserId());
        if (boke == null) {
            return to400View();
        }
        if (sysUser.getUserId().equals(boke.getUserId()) || sysUser.getRoles().contains("admin")) {
            String model = "login";
            Integer type = boke.getEditorType();
            if (null != type) {
                model = BookType.typeMap.get(type);
            }

            if (boke.getDeleteState() == 0) {
                return to400View("该帖子已被删除~");
            }

            Map<String, Object> map = new HashMap<>();
            if (boke.getBokeZip() == 1) {
                if (type == 2) {
                    boke.setMdContent(MyStringUtil.uncompress(boke.getMdContent()));
                } else {
                    boke.setBokeCont(MyStringUtil.uncompress(boke.getBokeCont()));
                }
            }
            map.put("boke", boke);
            map.put("bokeId", boke.getBokeId());
            return getView(model, map);
        } else {
            return to403View();
        }
    }

    @PostMapping("/t/addBoke")
    public ResultDto<String> addBoke(Bokes bokes) {
        SysUser sysUser = SecurityUtil.currentUser();
        if (sysUser == null) {
            return ResultDtoManager.fail(-1, "请先登录！");
        }
        Long userId = sysUser.getUserId();
        String html = bokes.getBokeCont();
        String rep = html.replaceAll("<p>", "").replaceAll("</p>", "").replaceAll("<br/>", "").replaceAll("&nbsp;", "").trim();
        if (rep.length() <= 0) {
            return ResultDtoManager.fail(-1, "请输入内容~");
        }
        String introduction = bokes.getIntroduction();
        if (!StrUtil.isBlank(introduction) && introduction.length() > Constants.BokeXZ.INCTRLEN) {
            bokes.setIntroduction(introduction.substring(0, Constants.BokeXZ.INCTRLEN));
        }
        String title = bokes.getTitle();
        if (title.length() > Constants.BokeXZ.INCTRLEN) {
            bokes.setIntroduction(introduction.substring(0, Constants.BokeXZ.INCTRLEN));
        }
        if (bokes.getBokeId() == null || bokes.getBokeId() == 0) {
            if (bokesService.checkTitle(bokes.getTitle())) {
                return fail(TITLEXIST);
            }
        } else {
            QueryWrapper<Bokes> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id", userId);
            wrapper.eq("boke_id", bokes.getBokeId());
            Bokes one = bokesService.getOne(wrapper);
            if (one == null) {
                return fail(NODATA);
            }
            bokes.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        }
        bokes.setUserId(userId);
//        对文章进行压缩，有js和后台压缩这里采用后台压缩
        // 因为可能存在汉字，如果存在汉字则不能使用length判断长度
        /*int length = MyStringUtil.getLength(html);
        if (length > Constants.BokeXZ.CONTLEN) {
            String compress = MyStringUtil.compress(html);
            bokes.setBokeCont(compress);
            if (Constants.BokeXZ.BIZTYPE.equals(bokes.getEditorType())) {
                bokes.setMdContent(MyStringUtil.compress(bokes.getMdContent()));
            }
            bokes.setBokeZip(1);
        } else {
            bokes.setBokeZip(0);
        }*/
        bokes.setBokeZip(0);
        boolean b = bokesService.addOrUpdateAndSetCache(bokes);
        return returnDto(b);
    }

    @RequestMapping("/getCateCount")
    public ResultDto<List<CategoryCount>> getCateCount(Integer userId) {
        List<CategoryCount> cateCount = bokesService.getCateCount(userId);
        return success(cateCount);
    }

    @RequestMapping("/getCreates")
    public ResultDto<List<CreateDates>> getCreates(Integer userId) {
        List<CreateDates> creates = bokesService.getCreates(userId);
        return success(creates);
    }

    @RequestMapping("/getLookups")
    public ResultDto<List<Lookups>> getLookups(Integer userId) {
        List<Lookups> lookups = bokesService.getLookups(userId);
        return success(lookups);
    }

    @RequestMapping("/setLookup/{bokeId}")
    public ResultDto<String> setLookUp(@PathVariable("bokeId") Integer bokeId) {
        int i = bokesService.setLookUp(bokeId);
        return returnDto(i);
    }

    @GetMapping("/setShareNum/{bokeId}")
    public ResultDto<String> setShareNum(@PathVariable("bokeId") Integer bokeId) {
        int i = bokesService.setShareNum(bokeId);
        return returnDto(i);
    }

    @RequestMapping("/setLikeNum/{bokeId}")
    public ResultDto<String> setLikeNum(@PathVariable("bokeId") Integer bokeId) {
        int i = bokesService.setLikeNum(bokeId);
        return returnDto(i);
    }

    /**
     * @param title
     * @return
     */
    @RequestMapping("/check/title")
    public ResultDto checktitle(String title) {
        boolean b = bokesService.checkTitle(title);
        if (b) {
            return fail(Constants.BokeXZ.TITLE_EXIST);
        }
        return success(Constants.BokeXZ.TITLE_USE);
    }
    
    @RequestMapping("/SLLNum/{bokeId}")
    public ResultDto getSLLNum(@PathVariable("bokeId") Integer bokeId) {
        String key1 = Constants.RedisKey.SHARENUM + bokeId;
        String key2 = Constants.RedisKey.LOOKNUM + bokeId;
        String key3 = Constants.RedisKey.LIKENUM + bokeId;
        Object o1;
        Object o2;
        Object o3;
        boolean b1 = redisUtil.hasKey(key1);
        boolean b2 = redisUtil.hasKey(key2);
        boolean b3 = redisUtil.hasKey(key3);
        if (b1 && b2 && b3) {
            o1 = redisUtil.get(key1);
            o2 = redisUtil.get(key2);
            o3 = redisUtil.get(key3);
        } else {
            Map<String, Object> sllNumById = bokesService.getSLLNumById(bokeId);
            o1 = sllNumById.get("share_num");
            o2 = sllNumById.get("look_num");
            o3 = sllNumById.get("like_num");
            if (!redisUtil.hasKey(key1)) {
                redisUtil.set(key1,o1);
            }
            if (!redisUtil.hasKey(key2)) {
                redisUtil.set(key2,o2);
            }
            if (!redisUtil.hasKey(key3)) {
                redisUtil.set(key3,o3);
            }
        }
        Map<String,Object> res = new HashMap<>(3);
        res.put("SHARENUM",o1);
        res.put("LOOKNUM",o2);
        res.put("LIKENUM",o3);
        return success(res);
    }
}
