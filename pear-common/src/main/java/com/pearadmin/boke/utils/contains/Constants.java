package com.pearadmin.boke.utils.contains;

import java.text.SimpleDateFormat;

public interface Constants {
    String WINDOWS = "WINDOWS";
    String ANDROID = "ANDROID";
    String IPHONE = "IPHONE";
    String MAC = "MAC";
    String PREHOST = "http://blog.suweibk.xyz/";

    // 日期格式化字符格式
    interface DateFormatStr {
        String DATESTR = "yyyy-MM-dd";
        String DATETIMESTR = "yyyy-MM-dd HH:mm:ss";
        String Y_MDATESTR = "yyyy-MM";
        String YMDATESTR = "yyyyMM";
        String YMDDATESTR = "yyyyMMdd";
    }

    interface UserStr {
        String FACE = "/imgs/face_2.jpg";
    }

    // 日期格式化
    interface DateFormat {
        /** 2020-06-22 */
        SimpleDateFormat DATE = new SimpleDateFormat(DateFormatStr.DATESTR);
        /** yyyy-MM-dd HH:mm:ss */
        SimpleDateFormat DATETIME = new SimpleDateFormat(DateFormatStr.DATETIMESTR);
        // yyyy-MM
        SimpleDateFormat S_DATE = new SimpleDateFormat(DateFormatStr.Y_MDATESTR);
        // yyyyMM
        SimpleDateFormat LDATE = new SimpleDateFormat(DateFormatStr.YMDATESTR);
        // yyyyMMdd
        SimpleDateFormat SDATE = new SimpleDateFormat(DateFormatStr.YMDDATESTR);
    }

    // 上传文件限制大小
    interface UploadFileSize {
        // 5M->KB
        long XZ5M = 5 * 1024 * 1024;
        // 10M->KB
        long XZ10M = 2 * XZ5M;
        // 20M->KB
        long XZ20M = 2 * XZ10M;
        // 200M->KB
        long XZ200M = 20 * XZ10M;
        // 文章内容图片限制
        long BOKEIMGMAXSIZE = XZ5M;
        // 头像图片限制
        long FACEIMGMAXSIZE = XZ5M;
        // 相册图片限制
        long PHOTOIMGMAXSIZE = XZ20M;
        // 文件大小限制
        long FILEMAXSIZE = XZ200M;
    }

    // 上传文件的目录
    interface UploadDir {
        String WD = "wds/";
        String MD = "mds/";
        String FACE = "faces/";
        String PHOTOS = "photos/";
        String OTHER = "other/";

        int OWN_WD = 1;
        int OWN_MD = 2;
        int OWN_FA = 3;
        int OWN_PH = 4;
        int OWN_OT = 5;

        int OSSTYPE_FTP = 1;
        int OSSTYPE_TX = 2;
        int OSSTYPE_ALI = 3;
        int OSSTYPE_QN = 4;

        String FILLPATH = "fillpath";
        String OOSKEY = "oosKey";
    }

    // redis使用的key
    interface RedisKey {
        String BD = "bangding"; // 绑定邮箱
        String GH = "genghua"; // 更换邮箱
        String MM = "genghuamm"; // 更换密码
        String PL = "pinglun"; // 评论
        String DAYVISIT = "visit"; // 访客
        String VISITCOUNT = "visitcount"; // 访客总量
        String QUES = "question"; // 发送问题邮件
        String SUBTITLE = "subtitle"; // 副标题
        String BOKEXQ = "bokexq_"; // 博客详情
        String NAVIGATION = "Navigation_"; // 个人导航
        String ALLNAVIGATIONY = "ALLNAVIGATIONY"; // 个人导航(已登录(普通用户))
        String ALLNAVIGATIONG = "ALLNAVIGATIONG"; // 个人导航(已登录(管理员))
        String ALLNAVIGATIONN = "ALLNAVIGATIONN"; // 个人导航(未登录)
        String OTHERNAVIGATION = "OTHERNAVIGATION"; // 自定义
        String USERINFO = "UserInfo_"; // 个人信息
        String GUIDANG = "guidang_"; // 归档日期
        String SONGLIST = "SongList_"; // 歌曲列表
        String LABELLIST = "LabelList_"; // 标签列表
        String CATELIST = "cateList_"; // 分类列表
        String SHARENUM = "SHARENUM_"; // 分享数
        String LOOKNUM = "LOOKNUM_"; // 浏览量
        String LIKENUM = "LIKENUM_"; // 点赞量
        String VIOLATION = "VIOLATION"; // 敏感词
        String DATEARCHIVE = "DATEARCHIVE"; // 日期归档
        String LOOKRANK = "LOOKRANK"; // 浏览排行
        String CATERANK = "CATERANK"; // 分类排行
        String LABELRANK = "LABELRANK"; // 标签排行
        String randomName = "randomName"; // 随即名称
        String USELABELLIST = "USELABELLIST"; // 可用标签列表
    }

    // 模板
    interface Template {
        // 邮箱模板
        String MAILTMPE = "mail.html";
    }

    // 每页显示条数
    interface PageSize {
        // 10条
        Integer SIZE10 = 10;
        Integer SIZE50 = 50;
        Integer SIZE30 = 30;
    }

//    博客表
    interface BokeXZ {
        // 摘要长度
        Integer INCTRLEN = 160;
        // 文章内容大于长度压缩
        long CONTLEN = 5 * 1024;
        // 压缩类型
        Integer BIZTYPE = 2;
        String TITLE_EXIST = "此标题已存在，请重新更换！";
        String TITLE_USE = "此标题可使用~";
        // 最大置顶数
        int ZD = 5;
        int CATELABELEN = 15;
    }

//  对象存储
    interface OOSStr {
        String HN = "hn";
        String QN_PRE = "http://www.suweibk.xyz/";
    }

    interface VideoSourceStr {
        Integer VY = 1;
        Integer ZY = 2;
        Integer MY = 3;
        Integer CY = 4;
    }
    interface TimeStr {
        // 5天时间的毫秒数
        Long DAY5 = 5 * 24 * 60 * 60L;
    }
}
