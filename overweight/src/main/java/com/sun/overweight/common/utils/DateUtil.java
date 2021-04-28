package com.sun.overweight.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

/**
 * @author
 * @date
 */
public class DateUtil {
    public static Integer compareDateByGetTime(Date date1, Date date2) {
        if (date1.getTime() < date2.getTime()) {
            return -1;
        } else if (date1.getTime() > date2.getTime()) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * 将日期 转换为 年月日时分的字符串
     * @param date
     * @return
     */
    public static String date2String(Date date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String dateString = sdf.format(date);
            return dateString;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * date2比date1多的天数
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1, Date date2)
    {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2)   //同一年
        {
            int timeDistance = 0 ;
            for (int i = year1 ; i < year2 ; i ++)
            {
                if (i%4==0 && i%100!=0 || i%400==0)    //闰年
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2-day1) ;
        }
        else    //不同年
        {
            return day2-day1;
        }
    }
    /**
     * 将日期字符串转换为java.util.Date类型
     *
     * @param dateStr   日期
     * @param formatStr 格式
     * @return
     * @author chenyk25600
     */
    public static Date parseDateStr(String dateStr, String formatStr) {
        if (dateStr == null || formatStr == null) {
            return null;
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat(formatStr);
            return format.parse(dateStr);
        } catch (ParseException e) {
//            log.error(e.getMessage(),e);
        }
        return null;
    }

    public static Date parseDateStrInt(Integer dateStr) {
        if (dateStr == null) {
            return null;
        }
        return parseDateStr(dateStr.toString(), "yyyyMMdd");
    }

    public static Date parseDateStr(String dateStr) {
        return parseDateStr(dateStr, "yyyyMMdd");
    }

    /**
     * 将java.util.Date类型转换为日期字符串
     *
     * @param date      日期
     * @param formatStr 格式
     * @return
     * @author chenyk25600
     */
    public static String formatDateStr(Date date, String formatStr) {
        if (date == null || formatStr == null) {
            return null;
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat(formatStr);
            return format.format(date);
        } catch (Exception e) {
//            log.error(e.getMessage(),e);
        }
        return null;
    }

    public static String formatChineseDateStr(Date date) {
        return formatDateStr(date, "yyyy年MM月dd日");
    }

    public static String formatDefaultDateStr(Date date) {
        return formatDateStr(date, "yyyy-MM-dd");
    }

    public static String formatDefaultDateStr2(Date date) {
        return formatDateStr(date, "yyyyMMdd");
    }

    public static String formatChineseTimeStr(Date date) {
        return formatDateStr(date, "yyyy年MM月dd日HH时mm分ss秒");
    }

    public static String formatDefaultTimeStr(Date date) {
        return formatDateStr(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String formatDefaultTimeStr2(Date date) {
        return formatDateStr(date, "yyyyMMddHHmmss");
    }

    public static Integer formatDefaultDateInt(Date date) {
        if (date == null) {
            return null;
        }
        return Integer.valueOf(formatDateStr(date, "yyyyMMdd"));
    }

    /**
     * 保留日期的年月日
     *
     * @param date 日期
     * @return
     * @author chenyk25600
     */
    public static Date toDate(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取截止日
     *
     * @param date 日期
     * @return
     * @author chenyk25600
     */
    public static Date toEndDate(Date date) {
        if (date == null) {
            return null;
        }
        return toDate(addDay(date, 1));
    }

    /**
     * 增加数量
     *
     * @param date 日期
     * @param num  数量
     * @param type 类型
     * @return
     * @author chenyk25600
     */
    public static Date add(Date date, int num, int type) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(type, num);
        return calendar.getTime();
    }

    public static Date addDay(Date date, int num) {
        return add(date, num, Calendar.DATE);
    }

    public static Date addMonth(Date date, int num) {
        return add(date, num, Calendar.MONTH);
    }

    public static Date addYear(Date date, int num) {
        return add(date, num, Calendar.YEAR);
    }

    public static Integer add(Integer date, int num, int type) {
        if (date == null) {
            return null;
        }
        Date date1 = parseDateStr(date.toString(), "yyyyMMdd");
        Date date2 = add(date1, num, type);
        return formatDefaultDateInt(date2);
    }

    public static Integer addDay(Integer date, int num) {
        return add(date, num, Calendar.DATE);
    }

    public static Integer addMonth(Integer date, int num) {
        return add(date, num, Calendar.MONTH);
    }

    public static Integer addYear(Integer date, int num) {
        return add(date, num, Calendar.YEAR);
    }

    /**
     * 根据类型获取
     *
     * @param date 日期
     * @param type 类型
     * @return
     */
    public static Integer get(Date date, int type) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(type);
    }

    public static Integer getYear(Date date) {
        return get(date, Calendar.YEAR);
    }

    public static Integer getMonth(Date date) {
        return get(date, Calendar.MONTH) + 1;
    }

    public static Integer getDay(Date date) {
        return get(date, Calendar.DAY_OF_MONTH);
    }


    /**
     * 两个日期之间的天数
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return
     * @author chenyk25600
     */
    public static long betweenDays(Date beginDate, Date endDate) {
        if (beginDate == null || endDate == null) {
            return 0;
        }
        LocalDate begin = dateToLocalDate(beginDate);
        LocalDate end = dateToLocalDate(endDate);
        return end.toEpochDay() - begin.toEpochDay();
    }

    public static long betweenDays(Integer beginDate, Integer endDate) {
        Date begin = parseDateStrInt(beginDate);
        Date end = parseDateStrInt(endDate);
        return betweenDays(begin, end);
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

    /**
     * localDate 转 date
     *
     * @param localDate
     * @return
     * @author chenyk25600
     */
    public static Date localDateToDate(LocalDate localDate) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
        return Date.from(zdt.toInstant());
    }

    public static Date endDate(Integer day) {
        Date date = parseDateStrInt(day);
        if (date != null) {
            date = addDay(date, 1);
        }
        return date;
    }
}
