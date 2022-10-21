package com.sun.overweight;

import com.sun.overweight.controller.UserController;
import com.sun.overweight.entity.RemoveHandler;
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
public class OverweightApplicationTests1 {
    @Autowired
    public static UserController userController;


    @Test
    public static void main(String[] args) throws Exception {
        List<RemoveHandler> input = new ArrayList<>();
        RemoveHandler removeHandler1 = RemoveHandler.builder().insNum("a").value(new BigDecimal("10.1")).build();
        RemoveHandler removeHandler2 = RemoveHandler.builder().insNum("b").value(new BigDecimal("30.3")).build();
        RemoveHandler removeHandler3 = RemoveHandler.builder().insNum("c").value(new BigDecimal("-20")).build();
        RemoveHandler removeHandler4 = RemoveHandler.builder().insNum("d").value(null).build();
        input.add(removeHandler1);
        input.add(removeHandler2);
        input.add(removeHandler3);
        input.add(removeHandler4);
        Boolean flag = true;
//        Boolean flag = false;
        if (flag){
            // 从小到大-nulllast
            input.sort(Comparator.nullsLast(Comparator.comparing(RemoveHandler::getValue,
                    Comparator.nullsLast(BigDecimal::compareTo))));
        }else {
            // 从大到小-nulllast
            input.sort(Comparator.nullsLast(Comparator.comparing(RemoveHandler::getValue,
                    Comparator.nullsFirst(BigDecimal::compareTo)).reversed()));
        }

        List<RemoveHandler> sortInput = new ArrayList<>(input);
//        Map<String, BigDecimal> results = sortInput.parallelStream()
//                .collect(LinkedHashMap::new, (m, v)->m.put(v.getInsNum(), v.getValue()), LinkedHashMap::putAll);
//        List<Map<String, BigDecimal>> list = Collections.synchronizedList(new ArrayList<>());
//        Set<Map.Entry<String, BigDecimal>> entries = results.entrySet();
//        for (Map.Entry entry : entries) {
//            String tempInsNum = (String) entry.getKey();
//            BigDecimal tempIndVal = (BigDecimal) entry.getValue();
//            Map<String, BigDecimal> currentMap = new HashMap<>();
//            currentMap.put(tempInsNum, tempIndVal);
//            list.add(currentMap);
//        }
//        List<RemoveHandler> pxlist = new ArrayList<>();
        for (int i = 0; i < sortInput.size(); i++) {
            System.out.println("insNum:" + sortInput.get(i) + ";排名:" + i );

        }

//        Set<Map.Entry<String, BigDecimal>> entries2 = results.entrySet();
//        for (Map.Entry entry : entries2) {
//            String tempInsNum = (String) entry.getKey();
//            BigDecimal tempIndVal = (BigDecimal) entry.getValue();
//            System.out.println("insNum:" + tempInsNum + ";排名:" + tempIndVal );
//        }
    }

    /**
     * 计算几何标准差
     *
     * @param values 计算数据数组
     * @return 几何标准差
     */
    private static double geoStandardDeviation(double[] values) {
        double v = 0.0;
        double sqrValue = 0.0;
        double powValue = 0.0;
        if (values != null) {
            int length = values.length;
            for (double d : values) {
                sqrValue = sqrValue + Math.pow(Math.log10(d), 2);
                powValue = powValue + Math.log10(d);
            }
            v = Math.pow(10, Math.sqrt((sqrValue - Math.pow(powValue, 2) / (double) length) / (double) (length - 1)));
        }
        return v;
    }

}
