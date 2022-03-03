package com.pearadmin.boke.utils.ip;

import com.pearadmin.boke.utils.ApiUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * IP帮助工具
 */
@Slf4j
public class IPHelper {
    /**
     * 得到用户的真实地址,如果有多个就取第一个
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ipNull(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ipNull(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipNull(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ipNull(ip)) {
            ip = request.getRemoteAddr();
        }
        log.info("获取的IP是：" + ip);
        String[] ips = ip.split(",");
        return ips[0].trim();
    }
    
    private static boolean ipNull(String ip) {
        return StrUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip);
    }

    /**
     * Tony （这个有些问题）
     * 多IP处理，可以得到最终ip
     *
     * @return
     */
    public static String getIp(HttpServletRequest request) {
        String ipAddr = IPHelper.getIpAddr(request);
        if ("0:0:0:0:0:0:0:1".equals(ipAddr) || "localhost".equals(ipAddr) || "127.0.0.1".equals(ipAddr)) {
            // 本地IP，如果没有配置外网IP则返回它  
            String localip = null;
            // 外网IP  
            String netip = null;
            try {
                Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
                InetAddress ip = null;
                // 是否找到外网IP  
                boolean finded = false;
                while (netInterfaces.hasMoreElements() && !finded) {
                    NetworkInterface ni = netInterfaces.nextElement();
                    Enumeration<InetAddress> address = ni.getInetAddresses();
                    while (address.hasMoreElements()) {
                        ip = address.nextElement();
                        if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && !ip.getHostAddress().contains(":")) {
                            // 外网IP  
                            netip = ip.getHostAddress();
                            finded = true;
                            break;
                        } else if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && !ip.getHostAddress().contains(":")) {
                            // 内网IP  
                            localip = ip.getHostAddress();
                        }
                    }
                }
            } catch (SocketException e) {
                return null;
            }
            log.info("获取的最终外网IP是：" + netip + "~内网IP为：" + localip);
            return StrUtil.isNotEmpty(netip) ? netip : localip;
        }
        return ipAddr;
    }

    /**
     * 调用接口获取ip地址
     *
     * @param ip
     */
    public static String getAddressByIp(String ip) {
        //查询Ip信息的接口，返回json 61.153.252.254
        String url = "http://ip.geo.iqiyi.com/cityjson?format=json&ip=" + ip;
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
//		{"code":"A00000","data":{"country":"中国","province":"浙江","city":"金华","country_id":48,"province_id":10,"city_id":10007,"location_id":26010007,"isp_id":26,"isp":"电信","longitude":0,"latitude":0,"ip":"61.153.252.254"}}
        //得到的json数据
		return result.toString();
    }

    /**
     * 根据ip查询所在城市
     * @param ip
     * @return
     */
    public static String getIpAddrByIp(String ip) {
        String urlApi = "http://ip.geo.iqiyi.com/cityjson";
        Map param = new HashMap();
        param.put("format","json");
        param.put("ip",ip);
        return ApiUtil.httpRequest(urlApi,param);
    }
    
    
}
