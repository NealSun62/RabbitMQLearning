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

import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.math3.linear.*;


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

            String targetFolderPath = "C:\\Users\\hspcadmin\\Desktop\\数据\\prc"; // 替换为实际的目标文件夹路径
            String outputFilePath = "C:\\Users\\hspcadmin\\Desktop\\数据\\prc_tmp.sql"; // 输出文件路径

            try {
                mergeSQLFiles(targetFolderPath, outputFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }


    }

    public static void mergeSQLFiles(String folderPath, String outputFilePath) throws IOException {
        File folder = new File(folderPath);
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".sql"));

        if (files == null || files.length == 0) {
            System.out.println("没有找到任何.sql文件");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            for (File file : files) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        writer.write(line);
                        writer.newLine();
                    }
                }
            }
        }

        System.out.println("合并完成，输出文件：" + outputFilePath);
    }
}
