package com.sun.overweight;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class OverweightApplicationTests11 {


    @Test
    public static void main(String[] args) {

        try {
            String add = "\\+";
            String filePath = "1+2+3+4";
            BigDecimal daybb = new BigDecimal("0.823654");
            String result = "";

            for (String tt : filePath.split(add)) {
                BigDecimal thisyear = new BigDecimal(tt);

                if (daybb.compareTo(thisyear) < 0) {
                    BigDecimal diff = thisyear.subtract(daybb);
                    diff = diff.setScale(4, BigDecimal.ROUND_HALF_UP);
                    result = diff.toString();
                    for (int y = Integer.parseInt(tt); y < filePath.split(add).length; y++) {
                        result = result + "+" + filePath.split(add)[y];
                    }
                    break;
                } else {
                    daybb = daybb.subtract(thisyear);
                }
            }
            System.out.println("result:" + result);

        } catch (Exception e) {
            e.printStackTrace();
        }

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