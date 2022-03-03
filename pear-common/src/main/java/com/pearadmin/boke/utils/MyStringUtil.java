package com.pearadmin.boke.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pearadmin.boke.utils.contains.Constants;

import cn.hutool.core.collection.CollectionUtil;
import sun.misc.BASE64Encoder;

/**
 * @Author lushao
 * @Description 字符串操作工具
 * @Date 2021/5/15 13:49
 * @Version 1.0
 */
public class MyStringUtil {
    static Logger logger = LoggerFactory.getLogger(MyStringUtil.class);
    // 编码方式
    private static final String ENCODE_UTF = "UTF-8";

    /**
     * 计算中英文字符串的字节长度 <br/>
     * 一个中文占3个字节
     *
     * @param str
     * @return int 字符串的字节长度
     */
    public static int getLength(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        try {
            return str.getBytes(ENCODE_UTF).length;
        } catch (UnsupportedEncodingException e) {
            logger.error("计算中英文字符串的字节长度失败，", e);
        }
        return 0;
    }

    /**
     * 计算中英文字符串的字节长度
     *
     * @param str
     * @return int
     */
    public static int getStrLength(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        int len = 0;
        for (int i = 0, j = str.length(); i < j; i++) {
            //UTF-8编码格式中文占三个字节，GBK编码格式 中文占两个字节 ;
            len += (str.charAt(i) > 255 ? 3 : 1);
        }
        return len;
    }

    /**
     * 使用gzip压缩字符串
     * @param str 要压缩的字符串
     * @return
     */
    public static String compress(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = null;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(str.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (gzip != null) {
                try {
                    gzip.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new BASE64Encoder().encode(out.toByteArray());
    }

    /**
     * 使用gzip解压缩
     * @param compressedStr 压缩字符串
     * @return
     */
    public static String uncompress(String compressedStr) {
        if (compressedStr == null) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPInputStream ginzip = null;
        byte[] compressed;
        String decompressed = null;
        try {
            compressed = new sun.misc.BASE64Decoder().decodeBuffer(compressedStr);
            ginzip = new GZIPInputStream(new ByteArrayInputStream(compressed));
            byte[] buffer = new byte[1024];
            int offset = -1;
            while ((offset = ginzip.read(buffer)) != -1) {
                out.write(buffer, 0, offset);
            }
            decompressed = out.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ginzip != null) {
                    ginzip.close();
                }
                out.close();
            } catch (IOException e) {
            }
        }
        return decompressed;
    }

    /**
     * 使用zip进行压缩
     *
     * @param str 压缩前的文本
     * @return 返回压缩后的文本
     */
    public static String zip(String str) {
        if (str == null) {
            return null;
        }
        ByteArrayOutputStream out = null;
        ZipOutputStream zout = null;
        String compressedStr = null;
        try {
            out = new ByteArrayOutputStream();
            zout = new ZipOutputStream(out);
            zout.putNextEntry(new ZipEntry("0"));
            zout.write(str.getBytes());
            zout.closeEntry();
            compressedStr = new BASE64Encoder().encodeBuffer(out.toByteArray());
        } catch (IOException e) {
        } finally {
            try {
                if (zout != null) {
                    zout.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
            }
        }
        return compressedStr;
    }

    /**
     * 使用zip进行解压缩
     *
     * @param compressedStr 压缩后的文本
     * @return 解压后的字符串
     */
    public static String unzip(String compressedStr) {
        if (compressedStr == null) {
            return null;
        }
        ByteArrayOutputStream out = null;
        ZipInputStream zin = null;
        String decompressed;
        try {
            byte[] compressed = new sun.misc.BASE64Decoder().decodeBuffer(compressedStr);
            out = new ByteArrayOutputStream();
            zin = new ZipInputStream(new ByteArrayInputStream(compressed));
            zin.getNextEntry();
            byte[] buffer = new byte[1024];
            int offset;
            while ((offset = zin.read(buffer)) != -1) {
                out.write(buffer, 0, offset);
            }
            decompressed = out.toString();
        } catch (IOException e) {
            return null;
        } finally {
            try {
                if (zin != null) {
                    zin.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {}
        }
        return decompressed;
    }

    /**
     * // 设置分子和分母
     * @param a
     * @param b
     * @return a:b
     */
    public static String getFs(int a, int b){
        int numerator;  // 分子
        int denominator; // 分母
        if(a == 0 || b == 0){
            numerator = a;
            denominator = b;
        }else{
            int c = f(Math.abs(a),Math.abs(b));
            numerator = a / c;
            denominator = b / c;
            if(numerator<0 && denominator<0){
                numerator = - numerator;
                denominator = - denominator;
            }
        }
        return numerator + ":" + denominator;
    }

    /**
     * // 求a和b的最大公约数
     * @param a
     * @param b
     * @return
     */
    private static int f(int a,int b){
        if(a < b){
            int c = a;
            a = b;
            b = c;
        }
        int r = a % b;
        while(r != 0){
            a = b;
            b = r;;
            r = a % b;
        }
        return b;
    }

    /**
     * 获取访问端系统
     * @param userAgent
     * @return
     */
    public static String getClient(String userAgent) {
        userAgent = userAgent.toUpperCase();
        String client;
        if (userAgent.contains(Constants.WINDOWS)) {
            client = "Windows";
        } else if (userAgent.contains(Constants.ANDROID)) {
            client = "Android";
        } else if (userAgent.contains(Constants.IPHONE)) {
            client = "IPhone";
        } else if (userAgent.contains(Constants.MAC)) {
            client = "Mac";
        } else {
            client = "Other";
        }
        return client;
    }
    
    public static Boolean isYellow(String[] str) {
        String[] ys = new String[] {"伦理","原创","在線視頻","女优资源","AV番号","GIF车牌","无码番号","有码番号","视觉VR","动漫肉番","番号资源","快乐看片","09116亚洲吞精","09116国产探花","09116麻豆传媒","无码","有码","亚洲有码","主播大秀","亚洲无码","欧美激情","中文字幕","成人动漫","三级伦理","乱轮剧情","三级仑理","欧美猛男","国产名人","空姐模特","抖阴视频","美熟少妇","91视频","中字馆","欧美馆","歐美視頻","亞洲無碼","亞洲自拍","成人特效","麻豆合集","无码馆","国产馆","素人馆","JAV馆","动漫馆","三级馆","伦理片","日韩无码","颜射系列","巨乳系列","人妻系列","自慰系列","强奸乱伦","制服诱惑","日韩无码","有码视频","日韩精品","颜射系列","大秀视频","口交视频","巨乳系列","伦理影片","人妻系列","3P合辑 ","自慰系列","自拍偷拍","强奸乱伦","SM重味","制服诱惑","日本无码"};
        return CollectionUtil.containsAny(Arrays.asList(ys), Arrays.asList(str));
    }

    /**
     * 把特殊字符替换成空串
     * @param html
     * @return
     */
    public static String replaceEmptyStr(String html) {
        html = html.replace("&amp;","").replace("&lt;","").replace("&gt;","").replace(" ","");
        String regEx= "[`~!@#$%^（）+=|{}':;,\\[\\].<>/?！￥…&*()—-【】‘；：”“’。，、？\\\\]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(html);
        return m.replaceAll("").trim();
    }

    /**
     * 是否含有违规字符
     * @param vlins
     * @param str
     * @return
     */
    public static Boolean isValinWord(Set<Object> vlins,String str) {
        for (Object vlin : vlins) {
            String s = (String) vlin;
            if (str.contains(s)) {
                return true;
            }
        }
        return false;
    }
    
}
