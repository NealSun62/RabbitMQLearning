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
import java.util.Base64;


@RunWith(SpringRunner.class)
@SpringBootTest
public class OverweightApplicationTests10 {


    @Test
    public static void main(String[] args) {
        try {
            // 指定要读取的txt文件路径
            String filePath = "E://file.txt";

            // 读取文件内容
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();

            // 将读取的内容转为JSON对象
            JSONObject jsonObject = new JSONObject(sb.toString());
            System.out.println("从文件中读取的JSON对象：" + jsonObject);

        } catch (IOException | JSONException e) {
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


}