package com.pearadmin.boke.conf.xss;

import cn.hutool.core.util.StrUtil;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * xss过滤包装类
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final static Logger logger = LoggerFactory.getLogger(XssHttpServletRequestWrapper.class);

    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getHeader(String name) {
        String header = super.getHeader(name);
        if (StrUtil.isEmpty(header)) {
            return header;
        }
        return Jsoup.clean(header, Whitelist.relaxed());
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values == null) {
            return null;
        }
        int length = values.length;
        String[] escapeValues = new String[length];
        for (int i = 0; i < length; i++) {
            //过滤一切可能的xss攻击字符串
            escapeValues[i] = Jsoup.clean(values[i], Whitelist.relaxed()).trim();
            if (!StrUtil.equals(escapeValues[i], values[i])) {
                logger.debug("xss字符串过滤前：" + values[i] + "\r\n" + "过滤后：" + escapeValues[i]);
            }
        }
        return escapeValues;
    }
}
