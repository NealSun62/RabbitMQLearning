package com.sun.overweight;

import com.sun.overweight.common.constant.Constants;
import com.sun.overweight.common.utils.DataUtil;
import com.sun.overweight.common.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.regex.Pattern;

import static com.sun.overweight.common.utils.DateUtil.formatDefaultDateStr2;
import static java.math.BigDecimal.valueOf;


@RunWith(SpringRunner.class)
@SpringBootTest
public class OverweightApplicationTests11 {


    @Test
    public static void main(String[] args) {
        String name = "NThmMWZkYTJkZjMxMjc1OQ==:YjQ4NmJmZDMxMjk2NTM0NA==";
        System.out.println(encrypt(name));

    }

    public static String calOptionLeftLimit(String optionBondMaturity, Integer expiredDate, Integer srteDate, Integer now ) {
        String result = "";
        if (srteDate == null || StringUtils.isBlank(optionBondMaturity) || expiredDate == null || (expiredDate == 20990101 && srteDate == 19000101)) {
            return result;
        }
        if (expiredDate < now) {
            return result;
        }
        String add = "\\+";
        try {
            String leftYear = optionBondMaturity.replace("年", "N");
            double passYearNum = calLeftLimit(now, DateUtil.parseDateStrInt(srteDate));
            BigDecimal passYear = valueOf(passYearNum);
            String[] tmp = leftYear.split(add);
            for (int i = 0; i < tmp.length; i++) {
                String tmpYear = tmp[i];
                if (!tmpYear.contains("N")){
                    BigDecimal yearNum = new BigDecimal(tmpYear);
                    if (passYear.compareTo(yearNum) < 0) {
                        BigDecimal diff = yearNum.subtract(passYear);
                        diff = diff.setScale(4, BigDecimal.ROUND_HALF_UP);
                        result = diff.toString();
                        for (int y = i + 1; y < tmp.length; y++) {
                            result = result + "+" + tmp[y];
                        }
                        break;
                    } else {
                        passYear = passYear.subtract(yearNum);
                    }
                }else {
                    if (("N").equals(tmpYear)) {
                        tmpYear = "1";
                    } else {
                        tmpYear = tmpYear.replace("N", "");
                    }
                    // 开始循环查询
                    BigDecimal yearNum = new BigDecimal(tmpYear);
                    while (passYear.compareTo(yearNum) > 0){
                        passYear = passYear.subtract(yearNum);
                    }
                    BigDecimal diff =  yearNum.subtract(passYear);
                    diff = diff.setScale(4, BigDecimal.ROUND_HALF_UP);
                    result = diff.toString();
                        result = result + "+" + tmp[i];
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 注释
     */
    private static final String PATTERN = "\\d{4}\\d{2}\\d{2}";

    public static Double calLeftLimit(Integer expiredDate, Date date) {
        /**
         * 分析：
         * 1.首先排除异常情况，即expiredDate不等于八位数字，排除
         * 2.若月日相等，则年份直接相减
         * 3.若月份大于今天，或则月份相等，但是日子大于今天，则直接年份相减，然后（当前年份拼上结束年份的年月 - 当前日子）/残端当年总天数
         * 4.如果不符合以上情况，则年份相减再减一，然后（当前年往后一年的年份拼上结束年份的年月 - 当前日子）/残端当年总天数
         */
        Date currDate = date;
        if (currDate == null) {
            currDate = new Date();
        }
        if (expiredDate == null || !Pattern.matches(PATTERN, String.valueOf(expiredDate))) {
            return null;
        }
        int bDate = DataUtil.getIntegerValue(formatDefaultDateStr2(currDate));
        // 首先判断两年的月日是否相等
        int bYear = bDate % 10000;
        int eYear = expiredDate % 10000;
        if (bYear == eYear) {
            return (expiredDate / 10000 - bDate / 10000) * 1.00;
        }

        // 判断月份大小
        boolean flag = bYear / 100 < eYear / 100
                || (bYear / 100 == eYear / 100 && bYear % 100 < eYear % 100);
        int betweenYear;
        int eDate;
        if (flag) {
            betweenYear = expiredDate / 10000 - bDate / 10000;
            eDate = DataUtil.getIntegerValue(bDate / 10000 + expiredDate.toString().substring(4, 8));
        } else {
            betweenYear = expiredDate / 10000 - bDate / 10000 - 1;
            eDate = DataUtil.getIntegerValue((bDate / 10000 + 1) + expiredDate.toString().substring(4, 8));
        }
        int stumpDays = getDaysBetweenDate(bDate, eDate) + 1;
        int stumpYDays = getDaysBetweenDate(bDate, bDate + 10000);

        return DataUtil.setDoubleScale(stumpDays * 1.00 / stumpYDays + betweenYear * 1.00, 4);
    }

    public static Double calLeftLimit(Integer expiredDate) {
        return calLeftLimit(expiredDate, new Date());
    }

    /**
     * 获取两个日期之间相隔天数
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static int getDaysBetweenDate(Integer beginDate, Integer endDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        if (beginDate != null && endDate != null) {
            try {
                Date bDate = format.parse(beginDate.toString());
                Date eDate = format.parse(endDate.toString());
                return getDaysBetweenDate(bDate, eDate);
            } catch (Exception e) {
            }
        }
        return 0;
    }

    /**
     * date 转 localDate
     *
     * @param date
     * @return
     * @author chenyk25600
     */
    public static LocalDate dateToLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDate();
    }

    public static int getDaysBetweenDate(Date beginDate, Date endDate) {
        if (beginDate != null && endDate != null) {
            try {
                LocalDate begin = dateToLocalDate(beginDate);
                LocalDate end = dateToLocalDate(endDate);
                return DataUtil.getIntegerValue(end.toEpochDay() - begin.toEpochDay());
            } catch (Exception e) {

            }
        }
        return 0;
    }

    /**
     * 加密
     */
    public static String encrypt(String str) {
        byte[] bytes = str.getBytes();
        return Base64.getEncoder().encodeToString(bytes);
    }


    public static boolean isInteger(double num) {
        return num == Math.floor(num);
    }

}