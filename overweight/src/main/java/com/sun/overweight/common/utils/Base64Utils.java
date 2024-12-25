package com.sun.overweight.common.utils;


import java.util.Base64;

/**
 * base64加密解密工具类
 *
 * @author gezc30201
 * @date 2020/11/18 9:52
 */
public class Base64Utils {


    /**
     * 加密
     */
    public static String encrypt(String str) {
        byte[] bytes = str.getBytes();
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * 解密
     */
    public static String decrypt(String encodedStr) {
        return new String(Base64.getDecoder().decode(encodedStr));
    }

}