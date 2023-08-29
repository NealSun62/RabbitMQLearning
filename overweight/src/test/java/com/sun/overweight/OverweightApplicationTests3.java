package com.sun.overweight;

import com.sun.overweight.common.constant.Constants;
import com.sun.overweight.controller.UserController;
import com.sun.overweight.entity.RemoveHandler;
import com.sun.overweight.entity.RemoveHandlerNum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @param
 * @author neal
 * @return
 * @date 2020/01/01
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OverweightApplicationTests3 {
    @Autowired
    public static UserController userController;


    @Test
    public static void main(String[] args) throws Exception {
        List<RemoveHandler> input = new ArrayList<>();
        RemoveHandler removeHandler1 = RemoveHandler.builder().insNum("a").value(new BigDecimal("100")).rankNum(1).build();
        RemoveHandler removeHandler2 = RemoveHandler.builder().insNum("b").value(new BigDecimal("90")).rankNum(2).build();
        RemoveHandler removeHandler3 = RemoveHandler.builder().insNum("c").value(new BigDecimal("20")).rankNum(3).build();
        RemoveHandler removeHandler4 = RemoveHandler.builder().insNum("d").value(new BigDecimal("15")).rankNum(4).build();
        RemoveHandler removeHandler5 = RemoveHandler.builder().insNum("e").value(new BigDecimal("0")).rankNum(5).build();
//        RemoveHandler removeHandler6 = RemoveHandler.builder().insNum("f").value(null).rankNum(6).build();
//        RemoveHandler removeHandler1 = RemoveHandler.builder().insNum("a").value(new BigDecimal("40")).rankNum(5).build();
//        RemoveHandler removeHandler2 = RemoveHandler.builder().insNum("b").value(new BigDecimal("30")).rankNum(4).build();
//        RemoveHandler removeHandler3 = RemoveHandler.builder().insNum("c").value(new BigDecimal("20")).rankNum(3).build();
//        RemoveHandler removeHandler4 = RemoveHandler.builder().insNum("d").value(new BigDecimal("16")).rankNum(2).build();
//        RemoveHandler removeHandler5 = RemoveHandler.builder().insNum("e").value(new BigDecimal("0")).rankNum(1).build();
        input.add(removeHandler1);
        input.add(removeHandler2);
        input.add(removeHandler3);
        input.add(removeHandler4);
        input.add(removeHandler5);
//        input.add(removeHandler6);
        String dir = "1";
//        String dir = "0";
        Collections.sort(input, Comparator.comparing(RemoveHandler::getRankNum));
        BigDecimal result = null;
        BigDecimal calVal = new BigDecimal("93");
        RemoveHandler first = input.get(0);
        RemoveHandler last = input.get(input.size() - 1);
        if (Constants.YES.equals(dir) || input.size() == 1) {
            if (calVal.compareTo(first.getValue()) >= 0) {
                result = new BigDecimal(String.valueOf(first.getRankNum()));
                System.out.println("正方向大于第一result:" + result);
                return;
            } else if (calVal.compareTo(last.getValue()) < 0) {
                result = new BigDecimal(String.valueOf(last.getRankNum()));
                System.out.println("正方向小于倒数第一result:" + result);
                return;
            }
        } else {
            if (calVal.compareTo(first.getValue()) <= 0) {
                result = new BigDecimal(String.valueOf(first.getRankNum()));
                System.out.println("反方向小于第一result:" + result);
                return;
            } else if (calVal.compareTo(last.getValue()) > 0) {
                result = new BigDecimal(String.valueOf(last.getRankNum()));
                System.out.println("反方向大于倒数第一result:" + result);
                return;
            }
        }
        for (int i = 0; i < input.size(); i++) {
            RemoveHandler removeHandler = input.get(i);
            BigDecimal value = removeHandler.getValue();
            BigDecimal gap = value.subtract(calVal).abs();
            if (gap.compareTo(BigDecimal.ZERO) == 0) {
                System.out.println("取值相同result:" + removeHandler.getRankNum());
                return;
            }
        }
        List<RemoveHandler> inputBiggerList = input.stream().filter(p -> p.getValue().compareTo(calVal) > 0).collect(Collectors.toList());
        List<RemoveHandler> inputLessList = input.stream().filter(p -> p.getValue().compareTo(calVal) < 0).collect(Collectors.toList());
        // 获取得分相邻两边的排名:大于该数的min和小于该数的最大值
        inputBiggerList.sort(Comparator.comparing(RemoveHandler::getValue, BigDecimal::compareTo).reversed());
        RemoveHandler bigMin = inputBiggerList.get(0);
        inputLessList.sort(Comparator.comparing(RemoveHandler::getValue, BigDecimal::compareTo));
        RemoveHandler smallMax = inputLessList.get(inputLessList.size() - 1);
        List<RemoveHandler> rankList = new ArrayList<>();
        rankList.add(bigMin);
        rankList.add(smallMax);
        // 取前二的位置
        if (Constants.YES.equals(dir)) {
            rankList.sort(Comparator.comparing(RemoveHandler::getValue, BigDecimal::compareTo).thenComparing(RemoveHandler::getPos, Comparator.reverseOrder()));
        } else {
            rankList.sort(Comparator.comparing(RemoveHandler::getValue, BigDecimal::compareTo).thenComparing(RemoveHandler::getPos));
        }
        if (rankList.size() >= 2) {
            BigDecimal highPos = new BigDecimal(String.valueOf(rankList.get(0).getPos()));
            BigDecimal highNum = rankList.get(0).getValue();
            BigDecimal lowPos = new BigDecimal(String.valueOf(rankList.get(1).getPos()));
            BigDecimal lowNum = rankList.get(1).getValue();
            BigDecimal rankGap = lowPos.subtract(highPos);
            BigDecimal calGap = calVal.subtract(lowNum);
            BigDecimal highLowGap = highNum.subtract(lowNum);
            // 低排名-（低排名-高排名）*（样本外分-低排名分）/(高排名分-低排名分)
            result = lowPos.subtract(rankGap.multiply(calGap).divide(highLowGap, 2, BigDecimal.ROUND_HALF_UP));
        } else {
            result = new BigDecimal(String.valueOf(rankList.get(0).getPos()));
        }
        System.out.println("result:" + result);
    }


}
