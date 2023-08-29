package com.sun.overweight;

import com.google.common.collect.Lists;
import com.sun.overweight.common.utils.CommUtil;
import com.sun.overweight.common.utils.DateUtil;
import com.sun.overweight.controller.UserController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @param
 * @author neal
 * @return
 * @date 2020/01/01
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OverweightApplicationTests5 {
    @Autowired
    public static UserController userController;


    @Test
    public static void main(String[] args) throws Exception {
        int size = 133;
        for (int i = 0; i < size; i++) {
            System.out.println(CommUtil.uuid());
        }
        String a = "";
        String b = null;
        String c = a+ b +"hej";
        System.out.println(c);
    }


    /**
     * 求在百分位p的值是多少
     *
     * @param dataList
     * @param p
     * @return
     */
    private static double getPercentile(List<Double> dataList, double p) {
        int n = dataList.size();
        dataList.sort(new Comparator<Double>() {
            //从小到大排序
            @Override
            public int compare(Double o1, Double o2) {
                if (o1 == null || o2 == null) {
                    return 0;
                }
                return o1.compareTo(o2);
            }
        });
        double px = p * (n - 1);
        int i = (int) Math.floor(px);
        double g = px - i;
        if (g == 0) {
            return dataList.get(i);
        } else {
            return (1 - g) * dataList.get(i) + g * dataList.get(i + 1);
        }
    }

    /**
     * 求在百分位p的值是多少
     *
     * @param data
     * @param p
     * @return
     */
    public static double percentile(double[] data, double p) {
        int n = data.length;
        Arrays.sort(data);
        double px = p * (n - 1);
        int i = (int) Math.floor(px);
        double g = px - i;
        if (g == 0) {
            return data[i];
        } else {
            return (1 - g) * data[i] + g * data[i + 1];
        }
    }


}
