package com.sun.overweight.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * 数据类型处理通用类
 * @author sunwx
 * @date
 */
@Slf4j
public class DataUtil {

    /**
     * 设置Double数据，保留几位小数
     * @param param 初始数据
     * @param scale 位数
     * @return
     * 1.如果Double数据为null，返回null
     * 2.如果Double数据为naN(即非数字类型)，返回naN
     * 3.如果Double数据为Infinite(即(+/-)1.0 / 0.0)，返回Infinity
     * 4.否则，保留有效小数位返回
     */
    public static Double setDoubleScale(Double param, Integer scale) {
        if (param == null || param.isNaN() || param.isInfinite()) {
            return param;
        }
        BigDecimal bigDecimal = new BigDecimal(param);
        return bigDecimal.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 获取Integer类型值
     * @param value 初始数据
     * @return int值
     */
    public static Integer getIntegerValue(Object value) {
        if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof Number) {
            return ((Number) value).intValue();
        } else {
            return Integer.valueOf(value.toString());
        }
    }

    public static Double stringToDouble(String valueStr, Integer scale) {
        try {
            double value = Double.parseDouble(valueStr);
            if (scale != null && scale >= 0) {
                return setDoubleScale(value, scale);
            }
            return value;
        } catch (Exception e) {
        	log.error(e.getMessage(),e);
            return null;
        }
    }

    /**
     * 元转亿元
     * @param data 元
     * @return 亿元
     */
    public static BigDecimal transToBillion(BigDecimal data) {
        if (data==null) {
            return data;
        }
        try {
            // 元转亿元fst_iss_scal
            return BigDecimal.valueOf(DataUtil.setDoubleScale(data.doubleValue()/100000000, 2));
        }catch (Exception e) {
            return data;
        }
    }

    /**
     * 元转万元
     * @param data 元
     * @return 万元
     */
    public static BigDecimal transToMillion(BigDecimal data) {
        if (data==null) {
            return data;
        }
        try {
            // 元转万元
            return BigDecimal.valueOf(DataUtil.setDoubleScale(data.doubleValue()/10000, 2));
        }catch (Exception e) {
            return data;
        }
    }

    /**
     * 小数转百分数
     * @param data
     * @return
     */
    public static BigDecimal transToPercent(BigDecimal data) {
        if (data==null) {
            return data;
        }
        try {
            // 元转亿元fst_iss_scal
            return BigDecimal.valueOf(DataUtil.setDoubleScale(data.doubleValue()*100, 2));
        }catch (Exception e) {
            return data;
        }
    }

}
