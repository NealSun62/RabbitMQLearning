package com.sun.overweight;

import com.alibaba.fastjson.JSON;
import com.sun.overweight.common.utils.TransUtil;
import com.sun.overweight.ramp.common.model.Users;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.imageio.ImageIO;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OverweightApplicationTests9 {

    public static void main(String[] args) throws Exception {
        List<List<String>> allDataList = new ArrayList<>();
        List<String> tableHeader = Arrays.asList("FXRH", "FXFL", "FXMC", "FXQC", "NBPJ");
        List<String> a = Arrays.asList("1", "2", "wanke", "wanke2", "A");
        List<String> a1 = Arrays.asList("2", "3", "主体", "w只停2", "BBB");
        List<String> a2 = Arrays.asList("3", "3", "主体3", "3", "C");
        allDataList.add(a);
        allDataList.add(a1);
        allDataList.add(a2);
        // 3.读取Excel模板，补充数据后落地到服务器指定路径下
        ClassPathResource classPathResource = new ClassPathResource("tmpl/o32/FXRXX.xls");
        InputStream is = classPathResource.getInputStream();
        String fileName = "ZTPJ" + System.currentTimeMillis() + ".xls";
        String outputPath = "E:\\" + fileName;
        File file = new File(outputPath);

        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 每个String数组代表一行数据
        try {
            POIFSFileSystem poifsFileSystem = new POIFSFileSystem(is);
            Workbook workbook = new HSSFWorkbook(poifsFileSystem);
            int currentRowIndex = 0;
            int sheetIndex = 0;
            String sheetNameToDelete = "默认模板sheet页";
            int delSheetIndex = workbook.getSheetIndex(sheetNameToDelete);
            if (delSheetIndex != -1) {
                workbook.removeSheetAt(sheetIndex);
            } else {
                System.out.println("Sheet not found: " + sheetNameToDelete);
            }
            for (List<String> rowData : allDataList) {
                // 创建新的sheet并重置行索引
                Sheet sheet = workbook.createSheet("主体评级第" + (++sheetIndex) + "页");

                // 在新sheet的第一行添加header数据
                Row headerRow = sheet.createRow(0);
                for (int i = 0; i < tableHeader.size(); i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(tableHeader.get(i));
                }

                currentRowIndex = 1; // 从第二行开始填充数据

                Row row = sheet.createRow(currentRowIndex++);
                for (int i = 0; i < rowData.size(); i++) {
                    Cell cell = row.createCell(i);
                    cell.setCellValue(rowData.get(i));
                }
            }

            try (FileOutputStream fos = new FileOutputStream(outputPath)) {
                workbook.write(fos);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getStackTrace());

        }

    }


}