package com.pearadmin.boke.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author Administrator
 * @Description 密码工具
 * @Date 2021/4/7 21:37
 * @Version 1.0
 */
public class PasswordUtil {

    /**
     * 密码加密
     * @param password
     * @return
     */
    public static String SHAPwd(String password) {
        // 密码加密
        // 创建SHA算法对象
        MessageDigest sha = null;
        try {
            sha = MessageDigest.getInstance("SHA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
//        对字节数组对象进行更新处理
        sha.update(password.getBytes());
//        进行哈希计算 并生成32位字符串
        String newPwd = new BigInteger(sha.digest()).toString(32);
        return newPwd;
    }
}
