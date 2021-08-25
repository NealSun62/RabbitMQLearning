package com.sun.overweight.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.quartz.CronExpression;

/**
 * @author sunwx33102
 * @description
 * @date 2021-05-13 10:40
 */
public class CronUtil {
    /**
     * 校验Cron表达式是否合法
     * @param expression
     * @return
     */
    public static boolean isValid(String expression) {
        return CronExpression.isValidExpression(expression);
    }

    /**
     * 根据表达式获取时间列表
     * @param expression
     * @return
     */
    public static List<String> getListByExpression(Date createDate, String expression) {
        List<String> timeList = new ArrayList<>();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            CronExpression cronExp= new CronExpression(expression);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(createDate);
            while (true) {
                Date triggerDate = cronExp.getNextValidTimeAfter(calendar.getTime());
                if (triggerDate == null) {
                    break;
                }
                timeList.add(sdf.format(triggerDate));
                calendar.setTime(triggerDate);
                calendar.add(Calendar.SECOND, 1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeList;
    }
}