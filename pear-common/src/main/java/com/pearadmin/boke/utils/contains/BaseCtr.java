package com.pearadmin.boke.utils.contains;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pearadmin.boke.vo.BootStrapResult;
import com.pearadmin.boke.vo.ResultDto;
import com.pearadmin.boke.vo.ResultDtoManager;

import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseCtr {
    public static final String SUCCESS = "操作成功~";
    public static final String VIOLATIONSTR = "内容违规，请重新输入~";
    public static final String ERRPARAM = "数据错误~";
    public static final String BOKEILL = "此文章非法~";
    public static final String FAILD = "操作失败~";
    public static final String SCSB = "上传失败~";
    public static final String LOGIN = "请先登录~";
    public static final String PFYZM = "发送验证码频繁，请稍后再试~";
    public static final String SXYZM = "验证码已失效，请重新发送验证码~";
    public static final String ERRYZM = "验证码错误，请重新填写~";
    public static final String RELOGIN = "没有此用户，请重新登录~";
    public static final String BINDMAIL = "邮箱已经绑定~";
    public static final String UNBINDMAIL = "邮箱未绑定~";
    public static final String QUESMAIL = "一个小时之内只能发送一封邮件哦~";
    public static final String NAMEERR = "用户名在4~16位之间(中文，字母，数字，下划线)！";
    public static final String MAILERR = "请输入正确的邮箱格式！";
    public static final String MAILNO = "该邮箱不可用！";
    public static final String EXISIT = "已存在，请重新来！";
    public static final String NULL = "不能为空！";
    public static final String CONTNULL = "内容" + NULL;
    public static final String TITLEXIST = "该标题已存在！";
    public static final String LOGINERR = "用户名或密码不正确！";
    public static final String NODATA = "此用户没有这篇文章！";
    public static final String NOBOKE = "您评论的文章不存在！";
    public static final String PLXZ = "每天只能评论10条，如有需要请联系管理员！";
    public static final String ZDERR = "已经置顶"+Constants.BokeXZ.ZD+"个了呢~";
    public static final String UWJERR = "没有符合条件的文件~";
    public static final String FILENULL = "请选择文件~";
    public static final String FILE5M = "文件必须在5M之内~";
    public static final String FILE_10M = "文件必须在10M之内~";
    public static final String FILE_20M = "文件必须在20M之内~";
    public static final String FILE_200M = "文件必须在200M之内~";
    public static final String PWDERR = "密码必须在6~15位之间(只能字母、数字、英文符号)！";
    public static final String NOTEXSIT = "不存在此条数据~";
    public static final String EXSIT = "已存在此数据~";
    public static final String NOTUSER = "该账户不可用~";
    public static final String NOCOMMENT = "此评论不存在~";

    protected ModelAndView getView(String modelName, Map<String, Object> param) {
        ModelAndView modelAndView = new ModelAndView(modelName);
        if (!ObjectUtil.isEmpty(param)) {
            modelAndView.getModel().putAll(param);
        }
        return modelAndView;
    }
    
    protected String outLen(int len) {
        return "内容不能超过" + len + "~";
    }

    protected ModelAndView to400View(String msg) {
        Map<String, Object> param = new HashMap<>();
        param.put("msg", msg);
        return getView("error/404", param);
    }

    protected ModelAndView to400View() {
        return getView("error/404", null);
    }

    protected ModelAndView to500View() {
        return getView("error/500", null);
    }

    protected ModelAndView to500View(String msg) {
        Map<String, Object> param = new HashMap<>();
        param.put("msg", msg);
        return getView("error/500", param);
    }

    protected ModelAndView toLoginView() {
        return getView("login", null);
    }

    protected BootStrapResult getBootStraps(IPage pages) {
        BootStrapResult result = new BootStrapResult<>(pages.getRecords(), pages.getTotal());
        return result;
    }

    protected ResultDto returnDto(boolean b) {
        return b ? success(SUCCESS) : fail(FAILD);
    }
    protected ResultDto resultExsit(boolean b) {
        return b ? success(SUCCESS) : fail(EXISIT);
    }

    protected ResultDto returnDto(int i) {
        return i > 0 ? success(SUCCESS) : fail(FAILD);
    }
    protected ResultDto returnDto(long i) {
        return i > 0 ? success(SUCCESS) : fail(FAILD);
    }

    protected ResultDto returnDto(Object o) {
        if (ObjectUtil.isNotEmpty(o)) {
            return success(o);
        }
        return fail(FAILD);

    }

    protected ResultDto success(Object o) {
        return ResultDtoManager.success(o);
    }

    protected ResultDto fail(String msg) {
        return ResultDtoManager.fail(-1, msg);
    }

    protected ResultDto success() {
        return ResultDtoManager.success(SUCCESS);
    }

    protected ResultDto fail() {
        return ResultDtoManager.fail(FAILD);
    }

    protected Map<String,Object> convertLayuiPage(IPage page) {
        Map<String, Object> json = new HashMap<>();
        json.put("msg", "success");
        json.put("code", "0");
        json.put("data", page.getRecords());
        json.put("count", page.getTotal());
        return json;
    }
    protected Map<String,Object> convertLayuiPage(List list) {
        Map<String, Object> json = new HashMap<>();
        json.put("msg", "success");
        json.put("code", "0");
        json.put("data", list);
        json.put("count", list.size());
        return json;
    }
    
}
