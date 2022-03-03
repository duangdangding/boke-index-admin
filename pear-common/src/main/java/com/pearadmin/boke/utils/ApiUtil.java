package com.pearadmin.boke.utils;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@Component
public class ApiUtil {

    /**
     * 处理post请求
     * @param httpUrl 请求url
     * @param param 参数 name=v1&name2=v2 格式
     * @return
     */
    public static String doPost(String httpUrl, String param) {

        HttpURLConnection connection = null;
        InputStream is = null;
        OutputStream os = null;
        BufferedReader br = null;
        String result = null;
        try {
            URL url = new URL(httpUrl);
            // 通过远程url连接对象打开连接
            connection = (HttpURLConnection) url.openConnection();
            // 设置连接请求方式
            connection.setRequestMethod("POST");
            // 设置连接主机服务器超时时间：15000毫秒
            connection.setConnectTimeout(15000);
            // 设置读取主机服务器返回数据超时时间：60000毫秒
            connection.setReadTimeout(60000);

            // 默认值为：false，当向远程服务器传送数据/写数据时，需要设置为true
            connection.setDoOutput(true);
            // 默认值为：true，当前向远程服务读取数据时，设置为true，该参数可有可无
            connection.setDoInput(true);
            // 设置传入参数的格式:请求参数应该是 name1=value1&name2=value2 的形式。
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // 设置鉴权信息：Authorization: Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0
            connection.setRequestProperty("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");
            // 通过连接对象获取一个输出流
            os = connection.getOutputStream();
            // 通过输出流对象将参数写出去/传输出去,它是通过字节数组写出的
            if (StrUtil.isNotBlank(param)) {
                os.write(param.getBytes());
            }
            // 通过连接对象获取一个输入流，向远程读取
            if (connection.getResponseCode() == 200) {

                is = connection.getInputStream();
                // 对输入流对象进行包装:charset根据工作项目组的要求来设置
                br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

                StringBuilder sbf = new StringBuilder();
                String temp = null;
                // 循环遍历一行一行读取数据
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                result = sbf.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // 断开与远程地址url的连接
            connection.disconnect();
        }
        return result;
    }

    /**
     * 调用api接口并返回数据 get请求
     * @param requestUrl
     * @param params
     * @return
     */
    public static String httpRequest(String requestUrl, Map params) {
        //buffer用于接受返回的字符
        StringBuilder buffer = new StringBuilder();
        try {
            //建立URL，把请求地址给补全，其中urlencode（）方法用于把params里的参数给取出来
            if (MapUtil.isNotEmpty(params)) {
                requestUrl += "?"+urlencode(params);
            }
            URL url = new URL(requestUrl);
            //打开http连接
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
            httpUrlConn.setDoInput(true);
            httpUrlConn.setRequestMethod("GET");
            httpUrlConn.connect();

            //获得输入
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            //将bufferReader的值给放到buffer里
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            //关闭bufferReader和输入流
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            //断开连接
            httpUrlConn.disconnect();
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回字符串
        return null;
    }

    public static String urlencode(Map<String,Object> data) {
        //将map里的参数变成像 showapi_appid=###&showapi_sign=###&的样子
        StringBuilder sb = new StringBuilder();
        data.forEach((key, value) -> {
            try {
                sb.append(key).append("=").append(URLEncoder.encode(value + "", "UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });
        return sb.substring(0, sb.length() - 1);
    }

    private static URL url;
    private static HttpURLConnection con;
    // 重连次数
    private static final int connectionNum = 1;
    private static int state = -1;

    /**
     * 功能：检测当前URL是否可连接或是否有效, 
     * 描述：最多连接网络 5 次, 如果 5 次都不成功，视为该地址不可用 
     * @param urlStr 指定URL网络地址 
     * @return 1不可用2黄色3,正常 0 错误
     */
    public synchronized int isConnect(String urlStr) {
        int counts = 0;
        if (StrUtil.isEmpty(urlStr)) {
            return 0;
        }
        while (counts < connectionNum) {
            try {
                url = new URL(urlStr);
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
//                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Content-Type", "text/html;Charset=utf-8;charset=UTF-8");
//                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36");
                state = con.getResponseCode();
                if (state == 200) {
                    StringBuilder result = new StringBuilder();
                    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
                    String line = "";
                    while (null != (line = br.readLine())) {
                        result.append(line);
                    }
                    br.close();
                    String s = result.toString();
                    log.error(urlStr + "~状态" + state + "内容：" + s);
                    boolean use = isUse(s);
                    if (use) {
                        return 1;
                    }
                    boolean yellow = isYellow(s);
                    if (yellow) {
                        return 2;
                    }
                    return 3;
                }
                break;
            }catch (Exception ex) {
                counts++;
                System.out.println("URL不可用，连接第 "+counts+" 次");
                url = null;
                continue;
            }
        }
        return 1;
    }
    public synchronized Integer isConnect2(String urlStr,Integer id) {
        try {
            url = new URL(urlStr);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
//                con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Content-Type", "text/html;Charset=utf-8;charset=UTF-8");
//                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36");
            state = con.getResponseCode();
            if (state == 200) {
                StringBuilder result = new StringBuilder();
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
                String line = "";
                while (null != (line = br.readLine())) {
                    result.append(line);
                }
                br.close();
                String s = result.toString();
                boolean use = isUse(s);
                if(use) {
                    log.error("不可用~" + urlStr + "~状态" + state + "内容：" + s);
                    return id;
                }
                boolean yellow = isYellow(s);
                if (yellow) {
                    log.error("YYYYYYYYYY~" + urlStr + "~状态" + state + "内容：" + s);
                    return id;
                }
            }
            id = null;
        }catch (Exception ex) {
            log.error("不可用~" + urlStr + "~状态" + state );
            url = null;
        }
        return id;
    }
    public synchronized void isConnect2(String urlStr) {
        try {
            url = new URL(urlStr);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
//                con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Content-Type", "text/html;Charset=utf-8;charset=UTF-8");
//                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36");
            state = con.getResponseCode();
            if (state == 200) {
                StringBuilder result = new StringBuilder();
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
                String line = "";
                while (null != (line = br.readLine())) {
                    result.append(line);
                }
                br.close();
                String s = result.toString();
                log.error(urlStr + "~状态" + state + "内容：" + s);
                boolean use = isUse(s);
                boolean yellow = isYellow(s);
            }
        }catch (Exception ex) {
            System.out.println("URL不可用");
            url = null;
        }
    }

    public synchronized String isConnect3(String urlStr) {
        try {
            url = new URL(urlStr);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "text/html;Charset=utf-8;charset=UTF-8");
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36");
            state = con.getResponseCode();
            StringBuilder result = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
            String line = "";
            while (null != (line = br.readLine())) {
                result.append(line);
            }
            br.close();
            String s = result.toString();
            if (state == 200) {
                if (isUse(s)) {
                    return null;
                }
                return s;
            } else {
                return null;
            }
        }catch (Exception ex) {
            System.out.println("URL不可用");
            url = null;
        }
        return null;
    }
    /**
     * 是不是有色
     * @param s
     * @return
     */
    private static boolean isYellow(String s) {
        String[] yells = {"无码","伦理","偷拍"};
        for (String yell : yells) {
            if (s.contains(yell)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否可用
     * @param s
     * @return
     */
    private static boolean isUse(String s) {
        // String[] yells = {"close","found","err","授权","403","503","错误","404","hao123","移步"};
        String[] yells = {"域名未授权","closed","File not found.","Not Found."};
        for (String yell : yells) {
            if (s.equals(yell)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        ApiUtil apiUtil = new ApiUtil();
//        String api = "https://www.80s.tw/";
//        int connect = apiUtil.isConnect(api);
        String url = "https://gitee.com/magerlu/source/raw/master/editor/wangeditor/emoji/giteeemoji.json";
//        String s = doPost(url, null);
        String s = httpRequest(url, null);
        System.out.println(s);

    }
    
}
