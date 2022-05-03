package com.sun.overweight;

import com.sun.overweight.controller.UserController;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

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
        int four = 2;
        System.out.println("ln:" + Math.log(four));
        System.out.println("根号:" + Math.sqrt(four));
        System.out.println("log10:" +Math.log10(four));
        System.out.println("平方:" +  Math.pow(four, 2));
        System.out.println("81的开方(pow)："+Math.pow(four, 1.0/3.0));

    }

    public Set<String> getExcelHeaders(String fileUrl) throws Exception {
        File file = new File(fileUrl);
        InputStream is = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(is);
        Sheet sheet = workbook.getSheetAt(0);
        System.out.println(sheet.getLastRowNum());
        //获取 excel 第一行数据（表头）
        Row row = sheet.getRow(0);
        //存放表头信息
        Set<String> set = new HashSet<>();
        //算下有多少列
        int colCount = sheet.getRow(0).getLastCellNum();
        System.out.println(colCount);
        for (int j = 0; j < colCount; j++) {
            Cell cell = row.getCell(j);
            String cellValue = cell.getStringCellValue().trim();
            set.add(cellValue);
        }
        return set;
    }

}
