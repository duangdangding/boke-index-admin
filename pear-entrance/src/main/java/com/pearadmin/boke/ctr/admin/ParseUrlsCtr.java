package com.pearadmin.boke.ctr.admin;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pearadmin.boke.service.ParseUrlsService;
import com.pearadmin.boke.entry.ParseUrls;
import com.pearadmin.boke.utils.ApiUtil;
import com.pearadmin.boke.utils.contains.BaseCtr;
import com.pearadmin.boke.utils.contains.Constants;
import com.pearadmin.boke.utils.exception.MyAssert;
import com.pearadmin.boke.vo.ResultDto;
import com.pearadmin.boke.vo.query.QuerySourceVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;

/**
 * ParseUrls控制层
 *
 * @author lushao
 * @version 1.0.0 2021-07-03 11:13:41
 */
@RestController
public class ParseUrlsCtr extends BaseCtr {

    @Autowired
    private ParseUrlsService parseUrlsService;

    @Autowired
    private ApiUtil apiUtil;

    @RequestMapping("/admin/parse/page")
    public Map<String,Object> getParsePage(QuerySourceVo vo) {
        IPage<ParseUrls> parsePage = parseUrlsService.getParsePage(vo);
        return convertLayuiPage(parsePage);
    }

    @RequestMapping("/admin/parse/shoudong")
    public ResultDto setShoudong(String ids) {
        int i = parseUrlsService.setShoudong(ids);
        return returnDto(i);
    }
    @RequestMapping("/admin/parse/deleteState")
    public ResultDto setDeleteState(String ids) {
        int i = parseUrlsService.setDeleteState(ids);
        return returnDto(i);
    }

    @RequestMapping("/parses")
    public ModelAndView videoList() {
        QueryWrapper<ParseUrls> wrapper = new QueryWrapper<>();
        wrapper.eq("parse_active",1);
        wrapper.eq("shoudong",0);
        wrapper.orderByDesc("parse_order");
        wrapper.orderByDesc("create_time");
        List<ParseUrls> list = parseUrlsService.list(wrapper);
        Map<String,Object> map = new HashMap<>();
        map.put("parses",list);
        return getView("parse",map);
    }

    @RequestMapping("/parsesmagerlu")
    public ModelAndView parses() {
        QueryWrapper<ParseUrls> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("parse_order");
        wrapper.orderByDesc("create_time");
        List<ParseUrls> list = parseUrlsService.list(wrapper);
        Map<String,Object> map = new HashMap<>();
        map.put("parses",list);
        return getView("parsemagerlu",map);
    }

    @RequestMapping("/par/{parseId}")
    public ResultDto getParseByiD(@PathVariable("parseId") Integer parseId) {
        ParseUrls parse = parseUrlsService.getById(parseId);
        return success(parse);
    }

    @PostMapping("/parEdit")
    public ResultDto eidtParse(ParseUrls parse) {
        boolean b = parseUrlsService.updateById(parse);
        return returnDto(b);
    }

    @RequestMapping("/parDel/{parseId}")
    public ResultDto del(@PathVariable("parseId") Integer parseId) {
        boolean b = parseUrlsService.removeById(parseId);
        return returnDto(b);
    }

    @RequestMapping("/jsonJXAdd")
    public ResultDto addJX(String jsonStr, String api, String name) {
        MyAssert.notBlank(jsonStr);
        boolean json = JSONUtil.isJson(jsonStr);
        if (json) {
            boolean jsonObj = JSONUtil.isJsonObj(jsonStr);
            if (jsonObj) {
                JSONObject e = JSONUtil.parseObj(jsonStr);
                String url = e.get(api).toString().trim();
                String apiName = e.get(name).toString();
                if (StrUtil.isEmpty(apiName)) {
                    apiName = RandomUtil.randomString(6);
                }
                List list = parseUrlsService.getByApi(url);
                MyAssert.unique(list);
                ParseUrls videoSource = new ParseUrls();
                videoSource.setParseUrl(url);
                videoSource.setParseName(apiName);
                boolean save = parseUrlsService.save(videoSource);
                return returnDto(save);
            } else {
                JSONArray objects = JSONUtil.parseArray(jsonStr);
                Iterable<JSONObject> jsonObjects = objects.jsonIter();
                List<ParseUrls> adds = new ArrayList<>();
                StringBuilder exsit = new StringBuilder();
                AtomicLong size = new AtomicLong();
                jsonObjects.forEach(e -> {
                    String url = e.get(api).toString().trim();
                    Object o = e.get(name);
                    String apiName;
                    if (ObjectUtil.isEmpty(o)) {
                        apiName = RandomUtil.randomString(6);
                    } else {
                        apiName = o.toString();
                    }
                    List<ParseUrls> apis = parseUrlsService.getByApi(url);
                    if (CollectionUtil.isEmpty(apis)) {
                        ParseUrls videoSource = new ParseUrls();
                        videoSource.setParseUrl(url);
                        videoSource.setParseName(apiName);
                        adds.add(videoSource);
                    } else {
                        exsit.append(apiName).append("：").append(url).append("<br>");
                    }
                    size.getAndIncrement();
                });
                boolean b = parseUrlsService.saveBatch(adds);
                String fail = "";
                if (exsit.length() > 0) {
                    fail = "已存在" + (size.get() - adds.size()) + "<br>" + exsit.toString();
                }
                if (b) {
                    return success("添加成功~" + adds.size() + fail);
                }
                return fail("发生错误" + fail);
            }
        } else {
            String[] parses = jsonStr.split(",");
            if (ArrayUtil.isNotEmpty(parses)) {
                List<ParseUrls> adds = new ArrayList<>();
                StringBuilder exsit = new StringBuilder();
                AtomicLong size = new AtomicLong();
                for (String pars : parses) {
                    pars = pars.trim();
                    if (pars.contains("\"")) {
                        pars = pars.replace("\"","");
                    }
                    List<ParseUrls> apis = parseUrlsService.getByApi(pars);
                    if (CollectionUtil.isEmpty(apis)) {
                        ParseUrls videoSource = new ParseUrls();
                        videoSource.setParseUrl(pars);
                        videoSource.setParseName(RandomUtil.randomString(6));
                        adds.add(videoSource);
                    } else {
                        exsit.append(pars).append("<br>");
                    }
                    size.getAndIncrement();
                }
                boolean b = parseUrlsService.saveBatch(adds);
                String fail = "";
                if (exsit.length() > 0) {
                    fail = "已存在" + (size.get() - adds.size()) + "<br>" + exsit.toString();
                }
                if (b) {
                    return success("添加成功~" + adds.size() + fail);
                }
                return fail("发生错误" + fail);
            }
        }
        return fail("不是正确的数据格式~");
    }

    /**
     *
     * @param type 1 默认2残影
     * @return
     */
    @RequestMapping("/downJXJson")
    public ResultDto down(Integer type) {
        QueryWrapper<ParseUrls> wrapper = new QueryWrapper<>();
        wrapper.eq("parse_active",1);
        wrapper.eq("shoudong",0);
        List<ParseUrls> urls = parseUrlsService.list(wrapper);
        MyAssert.exsitData(urls);
        List<Map<String, String>> maps = converZyparses(urls);
        String jsonStr = JSONUtil.toJsonStr(maps);
        String result ;
        if (type.equals(Constants.VideoSourceStr.VY)) {
            result = JSONUtil.formatJsonStr(jsonStr);
        } else {
            result = converCyJx(urls);
        }
        return success(result);
    }

    protected String converCyJx(List<ParseUrls> urls) {
        StringBuffer sb = new StringBuffer();
        urls.forEach(p -> {
            sb.append(p.getParseName()).append(",").append(p.getParseUrl()).append("\n");
        });
        return sb.toString();
    }

    protected List<Map<String,String>> converZyparses(List<ParseUrls> urls) {
        List<Map<String,String>> zyParse = new ArrayList<>();
        urls.forEach(url -> {
            Map<String,String> mParse = new HashMap<>();
            mParse.put("name",url.getParseName());
            mParse.put("api",url.getParseUrl());
            zyParse.add(mParse);
        });
        return zyParse;
    }

    @RequestMapping("/setJXUse")
    public ResultDto setuse(ParseUrls parse) {
        parse.setParseActive(parse.getParseActive() == 1 ? 0 : 1);
        boolean b = parseUrlsService.updateById(parse);
        return returnDto(b);
    }

    @RequestMapping("/checkUrlparse2")
    public ResultDto jianceUrlandset(String url,Integer sourceId) throws ExecutionException, InterruptedException {
        // CompletableFuture<ResultDto<Integer>> resultDtoCompletableFuture = myAsync.checkUrl(videoSourceService, url, sourceId);
        // return resultDtoCompletableFuture.get();
        int type = apiUtil.isConnect(url);
        ParseUrls parse = new ParseUrls();
        parse.setParseId(sourceId);
        parse.setParseActive(type > 0 ? 1 : 0);
        parseUrlsService.updateById(parse);
        return success(type);
    }
}
