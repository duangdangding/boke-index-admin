package com.pearadmin.boke.ctr.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pearadmin.boke.service.ParseUrlsService;
import com.pearadmin.boke.service.SourceGroupService;
import com.pearadmin.boke.service.VideoSourceService;
import com.pearadmin.boke.entry.ParseUrls;
import com.pearadmin.boke.entry.SourceGroup;
import com.pearadmin.boke.entry.VideoSource;
import com.pearadmin.boke.utils.ApiUtil;
import com.pearadmin.boke.utils.contains.BaseCtr;
import com.pearadmin.boke.utils.contains.Constants;
import com.pearadmin.boke.utils.exception.MyAssert;
import com.pearadmin.boke.vo.MYPlayer;
import com.pearadmin.boke.vo.ResultDto;
import com.pearadmin.boke.vo.query.QuerySourceVo;
import com.pearadmin.boke.vo.SourceVo;

import com.pearadmin.boke.vo.ZyPlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

/**
 * VideoSource控制层
 *
 * @author lushao
 * @version 1.0.0 2021-06-30 19:28:16
 */
@RestController
public class VideoSourceCtr extends BaseCtr {

    @Autowired
    private VideoSourceService videoSourceService;

    @Autowired
    private ParseUrlsService parseUrlsService;

    @Autowired
    private SourceGroupService sourceGroupService;

//    @Autowired
//    private MyAsync myAsync;

    @Autowired
    private ApiUtil apiUtil;

    @RequestMapping("/descCount")
    public ResultDto descCount() {
        Map<String, Object> map = videoSourceService.countBycause();
        return success(map);
    }

    @RequestMapping("/admin/video/page")
    public Map<String,Object> getVideoSourcePage(QuerySourceVo vo) {
        IPage<VideoSource> videoSourcePage = videoSourceService.getVideoSourcePage(vo);
        return convertLayuiPage(videoSourcePage);
    }

    @RequestMapping("/admin/video/shoudong")
    public ResultDto setShoudong(String ids) {
        int i = videoSourceService.setShoudong(ids);
        return returnDto(i);
    }
    @RequestMapping("/admin/video/setType")
    public ResultDto setType(String ids,Integer type) {
        int i = videoSourceService.setType(ids,type);
        return returnDto(i);
    }
    @RequestMapping("/admin/video/setJx")
    public ResultDto setJx(String ids) {
        int i = videoSourceService.setJx(ids);
        return returnDto(i);
    }
    @RequestMapping("/admin/video/deleteState")
    public ResultDto setDeleteState(String ids) {
        int i = videoSourceService.setDeleteState(ids);
        return returnDto(i);
    }
    
    
    @RequestMapping("/videosource")
    public ModelAndView videoList(Integer type) {
        if (type == null) {
            type = 1;
        }
        Map<String,Object> map = new HashMap<>();
        map.put("v_type",type);
        return getView("videosource",map);
    }
    public ModelAndView videoList_bak(Integer type) {
        QueryWrapper<VideoSource> wrapper = new QueryWrapper<>();
//        wrapper.eq("source_see",1);
        wrapper.eq("web_available",0);
        wrapper.eq("shoudong",0);
        if (type == null) {
            type = 1;
        }
        wrapper.eq("source_type",type);
        wrapper.orderByDesc("create_time");
        List<VideoSource> list = videoSourceService.list(wrapper);
        Map<String,Object> map = new HashMap<>();
        map.put("sources",list);
        map.put("v_type",type);
        return getView("videosource",map);
    }
    @RequestMapping("/videosourcemagerlu")
    public ModelAndView videoListManager() {
        QueryWrapper<VideoSource> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time");
        wrapper.last("limit 0,100");
        List<VideoSource> list = videoSourceService.list(wrapper);
        Map<String,Object> map = new HashMap<>();
        map.put("sources",list);
        return getView("videosourcemagerlu",map);
    }

    @RequestMapping("/sour/{sourceId}")
    public ResultDto getByid(@PathVariable("sourceId") Integer sourceId) {
        VideoSource byId = videoSourceService.getById(sourceId);
        return returnDto(byId);
    }

    @RequestMapping("/sourEdit")
    public ResultDto add1(VideoSource videoSource,Integer isEdit) {
        if (isEdit == 1) {
            boolean save = videoSourceService.save(videoSource);
            return returnDto(save);
        } else {
            boolean b = videoSourceService.updateById(videoSource);
            return returnDto(b);
        }
    }

    /**
     * 手机端VY
     * @param jsonStr
     * @param api
     * @param name
     * @param type
     * @return
     */
    @RequestMapping("/jsonAdd2")
    public ResultDto add2(String jsonStr,String api,String name,Integer type) {
        MyAssert.notBlank(jsonStr);
        boolean json = JSONUtil.isJson(jsonStr);
        if (json) {
            boolean jsonObj = JSONUtil.isJsonObj(jsonStr);
            if (jsonObj) {
                JSONObject e = JSONUtil.parseObj(jsonStr);
                String url = e.get(api).toString().trim();
                if (!url.endsWith("/")) {
//                    url = StrUtil.sub(url,0,url.length() - 1);
                    url = url + "/";
                }
                String apiName = e.get(name).toString();
                if (StrUtil.isEmpty(apiName)) {
                    apiName = RandomUtil.randomString(6);
                }
                List list = videoSourceService.getByApi(url);
                MyAssert.unique(list);
                VideoSource videoSource = new VideoSource();
                videoSource.setSourceApi(url);
                videoSource.setSourceName(apiName);
                videoSource.setSourceType(type);
                boolean save = videoSourceService.save(videoSource);
                return returnDto(save);
            } else {
                JSONArray objects = JSONUtil.parseArray(jsonStr);
                Iterable<JSONObject> jsonObjects = objects.jsonIter();
                List<VideoSource> adds = new ArrayList<>();
                StringBuilder exsit = new StringBuilder();
                AtomicLong size = new AtomicLong();
                jsonObjects.forEach(e -> {
                    String url = e.get(api).toString().trim();
                    if (!url.endsWith("/")) {
                        url = url + "/";
                    }
                    Object o = e.get(name);
                    String apiName;
                    if (ObjectUtil.isEmpty(o)) {
                        apiName = RandomUtil.randomString(6);
                    } else {
                        apiName = o.toString();
                    }
                    List<VideoSource> apis = videoSourceService.getByApi(url);
                    if (CollectionUtil.isEmpty(apis)) {
                        VideoSource videoSource = new VideoSource();
                        videoSource.setSourceApi(url);
                        videoSource.setSourceName(apiName);
                        videoSource.setSourceType(type);
                        adds.add(videoSource);
                    } else {
                        exsit.append(apiName).append("：").append(url).append("<br>");
                    }
                    size.getAndIncrement();
                });
                return batchVideos(adds,exsit,size);
            }
        }
        return fail("不是正确的json");
    }

    /**
     * PC端VY
     * @param jsonStr
     * @param api
     * @param name
     * @param type
     * @param parse
     * @param group
     * @return
     */
    @RequestMapping("/jsonAdd4")
    public ResultDto add4(String jsonStr,String api,String name,Integer type,String parse,String group) {
        MyAssert.notBlank(jsonStr);
        boolean json = JSONUtil.isJson(jsonStr);
        if (json) {
            boolean jsonObj = JSONUtil.isJsonObj(jsonStr);
            if (jsonObj) {
                JSONObject e = JSONUtil.parseObj(jsonStr);
                String url = e.get(api).toString().trim();
                if (!url.endsWith("/")) {
//                    url = StrUtil.sub(url,0,url.length() - 1);
                    url = url + "/";
                }
                String apiName = e.get(name).toString();
                if (StrUtil.isEmpty(apiName)) {
                    apiName = RandomUtil.randomString(6);
                }
                List list = videoSourceService.getByApi(url);
                MyAssert.unique(list);
                VideoSource videoSource = new VideoSource();
                videoSource.setSourceApi(url);
                videoSource.setSourceName(apiName);
                videoSource.setSourceType(type);
                setGroupParse(e,parse,group,videoSource);
                boolean save = videoSourceService.save(videoSource);
                return returnDto(save);
            } else {
                JSONArray objects = JSONUtil.parseArray(jsonStr);
                Iterable<JSONObject> jsonObjects = objects.jsonIter();
                List<VideoSource> adds = new ArrayList<>();
                StringBuilder exsit = new StringBuilder();
                AtomicLong size = new AtomicLong();
                jsonObjects.forEach(e -> {
                    String url = e.get(api).toString().trim();
                    if (!url.endsWith("/")) {
                        url = url + "/";
                    }
                    Object o = e.get(name);
                    String apiName;
                    if (ObjectUtil.isEmpty(o)) {
                        apiName = RandomUtil.randomString(6);
                    } else {
                        apiName = o.toString();
                    }
                    List<VideoSource> apis = videoSourceService.getByApi(url);
                    if (CollectionUtil.isEmpty(apis)) {
                        VideoSource videoSource = new VideoSource();
                        videoSource.setSourceApi(url);
                        videoSource.setSourceName(apiName);
                        videoSource.setSourceType(type);
                        setGroupParse(e,parse,group,videoSource);
                        adds.add(videoSource);
                    } else {
                        exsit.append(apiName).append("：").append(url).append("<br>");
                    }
                    size.getAndIncrement();
                });
                return batchVideos(adds,exsit,size);
            }
        }
        return fail("不是正确的json");
    }

    private void setGroupParse(JSONObject e,String parse,String group,VideoSource videoSource) {
        Object o = e.get(parse);
        if (ObjectUtil.isNotEmpty(o)) {
            String parseApi = o.toString().trim();
            List<ParseUrls> parses = parseUrlsService.getByApi(parseApi);
            int parseId;
            if (CollectionUtil.isNotEmpty(parses)) {
                ParseUrls parseUrls = parses.get(0);
                parseId = parseUrls.getParseId();
            } else {
                ParseUrls parseUrls = new ParseUrls();
                parseUrls.setParseUrl(parseApi);
                parseUrls.setParseName(RandomUtil.randomString(6));
                parseUrlsService.save(parseUrls);
                parseId = parseUrls.getParseId();
            }
//                添加parse
            videoSource.setParseId(parseId);
        }
        Object g = e.get(group);
        if (ObjectUtil.isNotEmpty(g)) {
            String groupName = g.toString().trim();
            QueryWrapper<SourceGroup> wrapper = new QueryWrapper<>();
            wrapper.eq("group_name",groupName);
            SourceGroup sourceGroup = sourceGroupService.getOne(wrapper);
            int groupId;
            if (ObjectUtil.isNotEmpty(sourceGroup)) {
                groupId = sourceGroup.getGroupId();
            } else {
                sourceGroup.setGroupName(groupName);
                sourceGroupService.save(sourceGroup);
                groupId = sourceGroup.getGroupId();
            }
//                添加group
            videoSource.setGroupId(groupId);
        }
    }
    private ResultDto batchVideos(List<VideoSource> adds,StringBuilder exsit,AtomicLong size) {
        boolean b = videoSourceService.saveBatch(adds);
        String fail = "";
        if (exsit.length() > 0) {
            fail = "已存在" + (size.get() - adds.size()) + "<br>" + exsit.toString();
        }
        if (b) {
            return success("添加成功~" + adds.size() + fail);
        }
        return fail("发生错误" + fail);
    }
    
    /*@RequestMapping("/jsonFileAdd")
    public ResultDto add3(@PathVariable("jsonFile") MultipartFile jsonFile,String api,String name,
                          int exporType,String parse,String group) {
        String jsonStr = JsonUtil.readFile(jsonFile);
        if (exporType == 1) {
            return add2(jsonStr,api,name);
        } else if(exporType == 2){
            return add4(jsonStr,api,name,parse,group);
        }
        return fail();
    }*/

    @RequestMapping("/sourDel/{sourceId}")
    public ResultDto del(@PathVariable("sourceId") Integer sourceId) {
        boolean b = videoSourceService.removeById(sourceId);
        return returnDto(b);
    }

    /**
     * （下载json 生成json）数据源页面
     * @param vo
     * @return
     */
    @RequestMapping("/udownJson")
    public ResultDto down(QuerySourceVo vo) {
        String result = null;
        Integer appType = vo.getAppType();
        vo.setShoudong(0);
        vo.setActive(0);
        // vyplayer
        List<VideoSource> videoSources = videoSourceService.getListBywebAvai(vo);
        if (appType.equals(Constants.VideoSourceStr.VY)) {
            MyAssert.exsitData(videoSources);
            List<SourceVo> sourceVos = convertSourceVos(videoSources);
            result = JSONUtil.formatJsonStr(JSONUtil.toJsonStr(sourceVos));
        } else if (appType.equals(Constants.VideoSourceStr.ZY)) {
            MyAssert.exsitData(videoSources);
            List<ZyPlayer> zyPlayers = convertZYVos(videoSources);
            result = JSONUtil.formatJsonStr(JSONUtil.toJsonStr(zyPlayers));
        } else if (appType.equals(Constants.VideoSourceStr.MY)) {
            MyAssert.exsitData(videoSources);
            List<MYPlayer> myPlayers = convertMyPlayer(videoSources);
            result = JSONUtil.formatJsonStr(JSONUtil.toJsonStr(myPlayers));
        } else if (appType.equals(Constants.VideoSourceStr.CY)) {
            MyAssert.exsitData(videoSources);
            result = convertCyPlayer(videoSources);
        }
        return success(result);
    }

    protected String convertCyPlayer(List<VideoSource> videoSources) {
        StringBuffer sb = new StringBuffer();
        videoSources.forEach(vs -> {
            sb.append(vs.getSourceName()).append(",").append(vs.getSourceApi()).append(",")
                    .append(vs.getNeedParse() == 0 ? "不解析" : "解析").append("\n");
        });
        return sb.toString();
    }
    protected List<MYPlayer> convertMyPlayer(List<VideoSource> videoSources) {
        List<MYPlayer> myPlayers = new ArrayList<>();
        videoSources.forEach(vs -> {
            MYPlayer my = new MYPlayer();
            my.setKey(vs.getSourceName());
            my.setName(vs.getSourceName());
            my.setApi(vs.getSourceApi());
            String parseApi = vs.getParseApi();
            if (StrUtil.isNotEmpty(parseApi)) {
                my.setPlayUrl(parseApi);
            }
            // String category = vs.getCategory();
            // category = category.replace("[","").replace("]","").replace("\"","");
            // my.setCategories(vs.getCategory().split(","));
            my.setCategories(new String[]{});
            myPlayers.add(my);
        });
        return myPlayers;
    }

    @RequestMapping("/jsonFormat")
    public ResultDto jsonformat(String jsonStr) {
        MyAssert.notBlank(jsonStr);
        boolean json = JSONUtil.isJson(jsonStr);
        if (json) {
            String result = JSONUtil.formatJsonStr(jsonStr);
            return success(result);
        }
        return fail("不是正确的json");
    }

    @RequestMapping("/setUse")
    public ResultDto setuse(VideoSource videoSource) {
        Boolean sourceSee = videoSource.getSourceSee();
        videoSource.setSourceSee(!sourceSee);
        boolean b = videoSourceService.updateById(videoSource);
        return returnDto(b);
    }

    @RequestMapping("/setJx")
    public ResultDto setJx(VideoSource videoSource) {
        Integer needParse = videoSource.getNeedParse();
        needParse = needParse == 1 ? 0 : 1;
        videoSource.setNeedParse(needParse);
        int i;
        if (needParse == 1) {
            i = videoSourceService.updateSetJxName(videoSource);
        } else {
            i = videoSourceService.updateUnSetJxName(videoSource);
        }
        return returnDto(i);
    }

    @RequestMapping("/setYellow")
    public ResultDto setYellow(VideoSource videoSource) {
        Integer sourceType = videoSource.getSourceType();
        sourceType = sourceType == 1 ? 2 : 1;
        videoSource.setSourceType(sourceType);
        int i;
        if (sourceType == 2) {
            i = videoSourceService.updateSetYellowName(videoSource);
        } else {
            i = videoSourceService.updateUnSetYellowName(videoSource);
        }
        return returnDto(i);
    }

    @RequestMapping("/setAllSerach")
    public ResultDto setAllSerach(VideoSource videoSource) {
        Boolean useInSearchAll = videoSource.getUseInSearchAll();
        videoSource.setUseInSearchAll(!useInSearchAll);
        boolean b = videoSourceService.updateById(videoSource);
        return returnDto(b);
    }

    /**
     *  1不可用2黄色3,正常 0 错误
     * @param url
     * @return
     */
    @RequestMapping("/checkUrl")
    public ResultDto jianceUrl(String url) {
        int type = apiUtil.isConnect(url);
        return success(type);
    }

    @RequestMapping("/checkUrl2")
    public ResultDto jianceUrlandset(String url,Integer sourceId) throws ExecutionException, InterruptedException {
        // CompletableFuture<ResultDto<Integer>> resultDtoCompletableFuture = myAsync.checkUrl(videoSourceService, url, sourceId);
        // return resultDtoCompletableFuture.get();
        int type = apiUtil.isConnect(url);
        VideoSource source = new VideoSource();
        if (type > 0) {
            source.setSourceId(sourceId);
            if (type == 1) {
                source.setSourceSee(false);
            } else {
                source.setSourceSee(true);
                if (type == 2) {
                    source.setSourceType(2);
                } else if (type == 3) {
                    source.setSourceType(1);
                }
            }
            videoSourceService.updateById(source);
        }
        return success(type);
    }

    protected VideoSource convertVideoSource(SourceVo sourceVo) {
        VideoSource videoSource = new VideoSource();
        videoSource.setSourceApi(sourceVo.getApi());
        videoSource.setSourceName(sourceVo.getName());
        videoSource.setUseInSearchAll(sourceVo.getUseInSearchAll());
        videoSource.setSourceSee(true);
        videoSource.setSourceType(1);
        return videoSource;
    }
    //    videosource转vy
    protected SourceVo convertSourceVo(VideoSource videoSource,int i) {
        SourceVo sourceVo = new SourceVo();
        sourceVo.setId(i);
        sourceVo.setApi(videoSource.getSourceApi());
        sourceVo.setName(videoSource.getSourceName());
        sourceVo.setUseInSearchAll(videoSource.getUseInSearchAll());
        return sourceVo;
    }
    //    
    protected ZyPlayer convertZYVo(VideoSource videoSource,int i) {
        ZyPlayer zy = new ZyPlayer();
        zy.setId(i);
        zy.setId(videoSource.getSourceId());
//        zy.setActive(videoSource.getSourceSee());
        zy.setKey(RandomUtil.randomString(6));
        zy.setName(videoSource.getSourceName());
        zy.setApi(videoSource.getSourceApi());
        zy.setDownload("");
        zy.setJiexiUrl(StrUtil.isEmpty(videoSource.getParseApi())?"":videoSource.getParseApi());
        zy.setGroup(videoSource.getGroupName());
        return zy;
    }

    protected List<VideoSource> convertVideosources(List<SourceVo> sourceVos) {
        List<VideoSource> result = new ArrayList<>();
        for (SourceVo sourceVo : sourceVos) {
            result.add(convertVideoSource(sourceVo));
        }
        return result;
    }
    //    List转换成VY的格式
    protected List<SourceVo> convertSourceVos(List<VideoSource> videoSources) {
        List<SourceVo> sourceVos = new ArrayList<>();
        int i = 1;
        for (VideoSource videoSource : videoSources) {
            sourceVos.add(convertSourceVo(videoSource,i));
            i++;
        }
        return sourceVos;
    }
    //    转换成ZY的格式
    protected List<ZyPlayer> convertZYVos(List<VideoSource> videoSources) {
        List<ZyPlayer> zys = new ArrayList<>();
        int i = 1;
        for (VideoSource videoSource : videoSources) {
            zys.add(convertZYVo(videoSource,i));
        }
        return zys;
    }

}