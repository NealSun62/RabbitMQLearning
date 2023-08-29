package com.sun.overweight.common.utils;

import java.util.UUID;

/**
 * @description: 通用工具类
 * @author: chenyk25600
 * @date: 2019/12/16
 */
public class CommUtil {

    /**
     * 生成uuid
     *
     * @return
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }

}
