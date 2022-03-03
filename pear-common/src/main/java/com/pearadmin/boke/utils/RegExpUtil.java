package com.pearadmin.boke.utils;

import java.util.regex.Pattern;

/**
 * 正则验证
 */
public class RegExpUtil {
    /*
     * 用户名验证 必须是4-16位字母、数字、下划线（这里中文字母、数字、下划线是指任意组合，没有必须三类均包含）
     *  @param name
     *  @return
     */
    public static boolean checkName(String name) {
//        String regExp = "^[^0-9][\\w_]{4,16}$";
        String regExp = "^[\\u4e00-\\u9fa5_a-zA-Z0-9]{4,16}$";
        return name.matches(regExp);
    }

    /**
     * 检测网址是否包含http或者https
     *
     * @param url
     * @return
     */
    public static boolean checkHttps(String url) {
        /*String urlRegex = "https?://(\\w|-)+(\\.(\\w|-)+)+(/(\\w+(\\?(\\w+=(\\w|%|-)*(\\&\\w+=(\\w|%|-)*)*)?)?)?)+";
        Pattern pattern = Pattern.compile(urlRegex);
        Matcher matcher = pattern.matcher(url);
        return matcher.matches();*/
        return Pattern.compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\\\\\\\/])+$")
                .matcher(url).find();
    }

    /**
     * 密码验证 6-15位数字 字母 英文符号
     *
     * @param pwd
     * @return
     */
    public static boolean checkPwd(String pwd) {
        String r = "[a-zA-Z0-9`~!@#\\$%\\^&\\*\\(\\)-_=\\+\\[\\{\\]\\}\\\\\\|;:'\",<\\.>/\\?]{6,15}";
        boolean isValid = Pattern.compile(r).matcher(pwd).matches();
        /*String regExp = "^([a-zA-Z0-9!@#$%^&*()_?<>{}]){6,20}$";
        if(pwd.matches(regExp)) {
            return true;
        }*/
        return isValid;
    }

    /**
     * 验证邮箱
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        String regEx = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
        boolean matches = email.matches(regEx);
        return matches;
    }

    public static void main(String[] args) {
        String value = "123，，，1";
        System.out.println(checkPwd(value));
        String email = "2538808545@qq.com";
        System.out.println(checkEmail(email));
    }
}
