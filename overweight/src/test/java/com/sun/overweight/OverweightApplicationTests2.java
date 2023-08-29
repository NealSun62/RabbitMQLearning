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
import org.springframework.util.CollectionUtils;

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
public class OverweightApplicationTests2 {
    @Autowired
    public static UserController userController;


    @Test
    public static void main(String[] args) throws Exception {
        int targetSum = 12;
        int targetValue = 88;

        for (int a = 0; a <= targetSum; a++) {
            for (int b = 0; b <= targetSum; b++) {
                for (int c = 0; c <= targetSum; c++) {
                    for (int d = 0; d <= targetSum; d++) {
                        if (a + b + c + d == targetSum && a + 5 * b + 10 * c + 20 * d == targetValue) {
                            System.out.println("一元 = " + a + ", 五元 = " + b + ", 十元 = " + c + ", 二十元 = " + d);
                        }
                    }
                }
            }
        }

        System.out.println("No solution found.");
    }
}
