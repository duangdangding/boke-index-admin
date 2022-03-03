package com.pearadmin.boke.utils;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cn.hutool.core.util.XmlUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

public class MyXmlUtil {

    private static final String TYPENAME = "class";

    public static String getVideoType(String str) {
        String result;
        if (str.startsWith("\uFEFF")) {
            str = str.replace("\uFEFF","");
        }
        if (JSONUtil.isJson(str)) {
            result = jxJson(str);
        } else {
            if (str.startsWith("<!DOCTYPE")) {
                result = "";
            } else {
                result = jxXml(str);
            }
        }
        return result;
    }

    public static String jxXml(String xmlStr) {
        Document document = XmlUtil.parseXml(xmlStr);
        // System.out.println(XmlUtil.format(xmlStr));
        NodeList rss = document.getElementsByTagName("rss");
        Node rssNode = rss.item(0);
        NodeList rssNodes = rssNode.getChildNodes();
        int length = rssNodes.getLength();
        if (length > 0) {
            for (int i = 0; i < rssNodes.getLength(); i++) {
                Node item = rssNodes.item(i);
                if (item.getLocalName().equals(TYPENAME)) {
                    NodeList tys = item.getChildNodes();
                    StringBuilder sb = new StringBuilder();
                    for (int j = 0; j < tys.getLength(); j++) {
                        Node item1 = tys.item(j);
                        sb.append(item1.getTextContent()).append(",");
                    }
                    return sb.substring(0,sb.length() - 1);
                    /*List<String> cats = new ArrayList<>();
                    for (int j = 0; j < tys.getLength(); j++) {
                        Node item1 = tys.item(j);
                        cats.add(item1.getTextContent());
                    }
                    return cats.toArray(new String[0]);*/
                }
            }
        }
        // String[] strings = cats.toArray(new String[0]);
        // System.out.println(JSONUtil.toJsonStr(strings));
        return null;
    }

    public static String jxJson(String jsonStr) {
        // System.out.println(JSONUtil.formatJsonStr(jsonStr));
        JSONObject jsonObject = JSONUtil.parseObj(jsonStr);
        JSONArray types = JSONUtil.parseArray(jsonObject.get("class"));
        List<String> cats = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        types.forEach(ts -> {
            JSONObject tso = JSONUtil.parseObj(ts);
            sb.append((String) tso.get("type_name")).append(",");
        });
        return sb.substring(0,sb.length() - 1);
    }
}
