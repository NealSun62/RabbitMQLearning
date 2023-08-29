package com.sun.overweight;

import com.google.common.collect.Lists;
import com.sun.overweight.common.utils.DateUtil;
import com.sun.overweight.controller.UserController;
import com.sun.overweight.ramp.common.model.Users;
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
public class OverweightApplicationTests4 {
    @Autowired
    public static UserController userController;


    @Test
    public static void main(String[] args) throws Exception {
        int four = 2;
        System.out.println("ln:" + Math.log(four));
        System.out.println("根号:" + Math.sqrt(four));
        System.out.println("log10:" + Math.log10(four));
        System.out.println("平方:" + Math.pow(four, 2));
        System.out.println("81的开方(pow)：" + Math.pow(four, 1.0 / 3.0));
        Set<String> insNUms = new HashSet<>();
        insNUms.add(null);
        System.out.println(insNUms.size());
        System.out.println(insNUms);
        Integer limit = new BigDecimal(100).multiply(new BigDecimal("0.05"))
                .setScale(0, BigDecimal.ROUND_DOWN).intValue();
        System.out.println(limit);
        limit = new BigDecimal(10).multiply(new BigDecimal("0.05"))
                .setScale(0, BigDecimal.ROUND_DOWN).intValue();
        System.out.println(limit);
        limit = new BigDecimal(10).multiply(new BigDecimal("0.1"))
                .setScale(0, BigDecimal.ROUND_DOWN).intValue();
        System.out.println(limit);
        limit = new BigDecimal(10).multiply(new BigDecimal("0.09"))
                .setScale(0, BigDecimal.ROUND_DOWN).intValue();
        System.out.println(limit);
        limit = new BigDecimal(10).multiply(new BigDecimal("0.11"))
                .setScale(0, BigDecimal.ROUND_DOWN).intValue();
        System.out.println(limit);
        BigDecimal minusOne = new BigDecimal("4.13886714733E9");
        System.out.println(minusOne);
        minusOne = new BigDecimal("-.3117008454");
        System.out.println(minusOne.multiply(minusOne));
        List<BigDecimal> indxValList = new ArrayList<>();
        indxValList.add(minusOne);
        indxValList.add(new BigDecimal(limit));
        indxValList = indxValList.stream()
                .sorted(Comparator.comparingDouble(BigDecimal::doubleValue)).collect(Collectors.toList());

        long dates = DateUtil.betweenDays(20220617, 20230917);
        List<String> aaa = new ArrayList<>();
        aaa.add("1");
        List<List<String>> childPoolScrNums = Lists.partition(aaa, 2000);
        Integer pages = null;
        System.out.println("pages%位置的数：" + pages);

        String finDataDate = null;
        double[] values = new double[]{1, 2, 3};
        System.out.println("从小到大排序后位于1%位置的数：" + percentile(values, 0.01d));
        System.out.println("从小到大排序后位于33%位置的数：" + percentile(values, 0.33d));
        System.out.println("从小到大排序后位于66%位置的数：" + percentile(values, 0.66d));
        System.out.println("从小到大排序后位于75%位置的数：" + percentile(values, 0.75d));
        System.out.println("从小到大排序后位于80%位置的数：" + percentile(values, 0.8d));
        System.out.println("从小到大排序后位于85%位置的数：" + percentile(values, 0.85d));
        System.out.println("从小到大排序后位于90%位置的数：" + percentile(values, 0.9d));
        System.out.println("从小到大排序后位于100%位置的数：" + percentile(values, 1d));


        String kk = null;
        Map<String, String> mapmap = new HashMap<>();
        mapmap.put("a", "b");
        List<Users> usersList = new ArrayList<>();
        Users u1 = new Users();
        u1.setName(null);
        u1.setType("1");
        usersList.add(u1);
        Set<String> names = usersList.stream().map(Users::getName).collect(Collectors.toSet());
        System.out.println(names.toString());
        indxValList = indxValList.stream()
                .sorted(Comparator.comparingDouble(BigDecimal::doubleValue)).collect(Collectors.toList());
        if (mapmap.containsKey(kk)) {
            System.out.println("有kk");
        } else {
            System.out.println("没有kk");

        }
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
        int i = (int) java.lang.Math.floor(px);
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
        int i = (int) java.lang.Math.floor(px);
        double g = px - i;
        if (g == 0) {
            return data[i];
        } else {
            return (1 - g) * data[i] + g * data[i + 1];
        }
    }


}
