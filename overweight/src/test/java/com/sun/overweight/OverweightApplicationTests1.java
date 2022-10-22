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
        RemoveHandler removeHandler6 = RemoveHandler.builder().insNum("g").value(null).build();
        RemoveHandler removeHandler3 = RemoveHandler.builder().insNum("c").value(new BigDecimal("-20")).build();
        RemoveHandler removeHandler5 = RemoveHandler.builder().insNum("f").value(new BigDecimal("-20")).build();
        RemoveHandler removeHandler9 = RemoveHandler.builder().insNum("j").value(new BigDecimal("-30")).build();
        RemoveHandler removeHandler10 = RemoveHandler.builder().insNum("k").value(new BigDecimal("-20")).build();
        RemoveHandler removeHandler4 = RemoveHandler.builder().insNum("d").value(null).build();
        RemoveHandler removeHandler7 = RemoveHandler.builder().insNum("h").value(new BigDecimal("11.2")).build();
        RemoveHandler removeHandler8 = RemoveHandler.builder().insNum("i").value(new BigDecimal("11.2")).build();
        input.add(removeHandler1);
        input.add(removeHandler2);
        input.add(removeHandler5);
        input.add(removeHandler6);
        input.add(removeHandler9);
        input.add(removeHandler10);
        input.add(removeHandler7);
        input.add(removeHandler8);
        input.add(removeHandler3);
        input.add(removeHandler4);
//        Boolean flag = true;
        Boolean flag = false;
        if (flag) {
            // 从小到大-nulllast
            input.sort(Comparator.nullsLast(Comparator.comparing(RemoveHandler::getValue,
                    Comparator.nullsLast(BigDecimal::compareTo))));
        } else {
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
        BigDecimal flagNum = null;
        int rankNum = 1;
        int sameNum = 0;
        for (int i = 0; i < sortInput.size(); i++) {
            RemoveHandler removeHandler = sortInput.get(i);
            BigDecimal aa = removeHandler.getValue();
            if (i == 0) {
                flagNum = aa;
                System.out.println("insNum:" + removeHandler + ";排名one:" + rankNum);
            } else {
                if (aa != null && flagNum != null && flagNum.compareTo(aa) != 0) {
                    flagNum = aa;
                    rankNum = rankNum + 1;
                    rankNum = rankNum + sameNum;
                    System.out.println("insNum:" + removeHandler + ";排名two:" + rankNum);
                    sameNum = 0;
                } else if (aa == null && flagNum != null) {
                    flagNum = aa;
                    rankNum++;
                    rankNum = rankNum + sameNum;
                    System.out.println("insNum:" + removeHandler + ";排名three:" + rankNum);
                    sameNum = 0;
                } else if (aa != null && flagNum != null && flagNum.compareTo(aa) == 0) {
                    flagNum = aa;
                    sameNum = sameNum + 1;
                    System.out.println("insNum:" + removeHandler + ";排名four:" + rankNum);
                } else if (aa == null) {
                    rankNum = rankNum + sameNum;
                    System.out.println("insNum:" + removeHandler + ";排名five:" + rankNum);
                }
            }

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
