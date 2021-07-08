package com.sun.overweight.common.utils;/*
 * 软件版权: 恒生电子股份有限公司
 * 修改记录:
 * 修改日期     修改人员  修改说明
 * ========    =======  ============================================
 * 2019/4/28 19:13  zhangli25087  新增
 * ========    =======  ============================================
 */

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * 功能说明: 元素处理
 *
 * @author: zhangli25087 zhangli25087@hundsun.com <br>
 * @date: 2019/4/28 19:13 <br>
 * 功能描述: 写明作用，调用方式，使用场景，以及特殊情况<br>
 */
public class EmeUtils {

    private static final String NULL = "null";

    /**
     * 判断对象是否为空
     *
     * @param obj
     * @return
     */
    public static boolean isBlank(Object obj) {
        if (null == obj || NULL.equals(obj) || "".equals(obj)) {
            return true;
        }
        if (obj instanceof String) {
            if ("".equals(obj.toString().trim())) {
                return true;
            }
        } else if (obj instanceof List) {
            if (((List<?>) obj).size() == 0) {
                return true;
            }
        } else if (obj instanceof Map) {
            if (((Map<?, ?>) obj).size() == 0) {
                return true;
            }
        } else if (obj instanceof Set) {
            if (((Set<?>) obj).size() == 0) {
                return true;
            }
        } else if (obj instanceof Object[]) {
            if (((Object[]) obj).length == 0) {
                return true;
            }
        } else if (obj instanceof int[]) {
            if (((int[]) obj).length == 0) {
                return true;
            }
        } else if (obj instanceof long[]) {
            if (((long[]) obj).length == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断对象是否不为空
     *
     * @param obj
     * @return
     */
    public static boolean isNotBlank(Object obj) {
        return !isBlank(obj);
    }

    /**
     * 元素转字符串
     *
     * @param obj
     * @return
     */
    public static String emeToStr(Object obj) {
        if (obj == null) {
            return null;
        }
        return obj.toString().trim();
    }

    /**
     * obj转int
     *
     * @param obj
     * @return
     */
    public static Integer emeToInt(Object obj) {
        if (isBlank(obj)) {
            return null;
        }
        return Integer.parseInt(obj.toString());
    }

    /**
     * obj转BigDecimal
     *
     * @param obj
     * @return
     */
    public static BigDecimal emeToBigDecimal(Object obj) {
        if (isBlank(obj)) {
            return null;
        }
        return new BigDecimal(obj.toString());
    }

    /**
     * 方法简介: obj转BigDecimal
     * @author: zhangli25087 zhangli25087@hundsun.com
     * @date: 2020/7/21 20:30 <br>
     * @describe:
     * @param obj :
     * @param digs :保留位数
     * @return
     *
     */
    public static BigDecimal emeToBigDecimal(Object obj, Integer digs) {
        if (isBlank(obj)) {
            return null;
        }
        return new BigDecimal(obj.toString()).setScale(digs, BigDecimal.ROUND_HALF_UP);
    }


    /**
     * 方法简介: 判断两个字符串是否相等
     *
     * @author: zhangli25087 zhangli25087@hundsun.com
     * @date: 2018-11-23 09:52 <br>
     * 功能描述: 写明作用<br>
     */
    public static boolean equals(Object value1, Object value2) {
        if (value1 == null && value2 == null) {
            return true;
        }
        if (value1 == null || value2 == null) {
            return false;
        }
        if (value1.toString().equals(value2.toString())) {
            return true;
        }
        return false;
    }

    /**
     * 保留几位小数 返回千分位
     *
     * @param eme  数据
     * @param digs 保留的位数
     * @return
     */
    public static String emeDealDigits(Object eme, Integer digs) {
        if (eme == null) {
            return null;
        }
        String numStr = emeToStr(eme);
        if (isNumeric(numStr)) {
            DecimalFormat df = new DecimalFormat(getType(digs));
            return df.format(emeToDouble(numStr));
        }
        return numStr;
    }

    /**
     * 保留几位小数
     *
     * @param eme  数据
     * @param digs 保留的位数
     * @param unit 单位
     * @return
     */
    public static String emeDealDigitsUnit(Object eme, Integer digs, Double unit) {
        if (eme == null) {
            return null;
        }
        String numStr = emeToStr(eme);
        if (isNumeric(numStr)) {
            Double d = emeToDouble(eme);
            if (unit > 0) {
                d = d * unit;
            }
            DecimalFormat df = new DecimalFormat(getType(digs));
            return df.format(emeToDouble(d));
        }
        return numStr;
    }

    /**
     * 判断是否为数字字符串
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(Object obj) {
        String str = emeToStr(obj);
        Pattern pattern = compile("-?[0-9]+.?[0-9]+");
        Matcher isNum = pattern.matcher(str);
        if (isNum.matches()) {
            return true;
        }
        // 判断科学技术法
        pattern = compile("^[+-]?[\\d]+([.][\\d]*)?([Ee][+-]?[\\d]+)?$");
        isNum = pattern.matcher(str);
        if (isNum.matches()) {
            return true;
        }
        return false;
    }

    /**
     * 获取格式
     *
     * @param digs
     * @return
     */
    public static String getType(Integer digs) {
        String str = null;
        switch (digs) {
            case 1:
                str = "#,##0.0";
                break;
            case 2:
                str = "#,##0.00";
                break;
            case 3:
                str = "#,##0.000";
                break;
            case 4:
                str = "#,##0.0000";
                break;
            case 5:
                str = "#,##0.00000";
                break;
            default:
                str = "##0";
                break;

        }
        return str;
    }

    /**
     * obj转doule
     *
     * @param obj
     * @return
     */
    public static Double emeToDouble(Object obj) {
        if (isBlank(obj)) {
            return 0.00;
        }
        return Double.parseDouble(obj.toString());
    }


    /**
     * 方法简介:转译integer
     *
     * @param object :
     * @return
     * @author: zhangli25087 zhangli25087@hundsun.com
     * @date: 2019/5/16 22:36 <br>
     * @describe:
     */
    public static Integer emeToInteger(Object object) {
        if (object == null) {
            return null;
        } else if (object instanceof Integer) {
            return (Integer) object;
        } else if (object instanceof BigDecimal) {
            return ((BigDecimal) object).intValue();
        } else if (object instanceof String) {
            return emeToInt(object);
        } else {
            return null;
        }
    }

    /**
     * 获取32位的UUID
     *
     * @return
     */
    public synchronized static String getUUID32() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        return str.replace("-", "");
    }

    public Boolean sortCompareDesc() {
        return false;
    }

}
