package com.pearadmin.boke.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.pearadmin.boke.entry.Users;

/**
 * @Author Administrator
 * @Description token工具类
 * @Date 2021/4/7 20:40
 * @Version 1.0
 */
public class TokenUtil {

    /** The request holder. */
    private static ThreadLocal<Users> tokenHolder = new ThreadLocal<Users>();

    public static Integer USERID = null;
//    public static String TOKEN = null;

    public static Users getToken(){
        return tokenHolder.get();
    }

    public static void setToken(Users token){
        tokenHolder.set(token);
    }

    /**
     * Clean.
     */
    public static void clean() {
        if (tokenHolder.get() != null) {
            tokenHolder.remove();
        }
    }

    /**
     * 是否开始请求.
     *
     * @return true, if successful
     */
    public static boolean requestStarted() {
        return tokenHolder.get() != null;
    }

    /**
     * 生成token
     * @param users
     * @return
     */
    public static String createToken(Users users) {
        String token = JWT.create().withAudience(users.getUserId() + "",users.getUserName())
                .sign(Algorithm.HMAC256(users.getUserId() + ""));
        return token;
    }
}
