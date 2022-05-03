package com.sun.overweight;

import com.sun.overweight.controller.UserController;
import com.sun.overweight.ramp.common.model.Users;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
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
        //创建工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建用户工作表
        HSSFSheet sheet = workbook.createSheet("动态获取");
        //创建行
        HSSFRow rows = sheet.createRow(0);
        //动态添加的所有表头行构成一个集合headList
        List<List<String>> headList = new ArrayList<List<String>>();
        List<Map> mapList = new ArrayList<>();
        Map map0 = new HashMap();
        map0.put("form_title", "指标1");
        Map map2 = new HashMap();
        map2.put("form_title", "指标2");
        mapList.add(map0);
        mapList.add(map2);
        // 第 n 行 的表头
        int j = 0;
        for (Map map : mapList) {
            //创建列
            rows.createCell(j).setCellValue((String) map.get("form_title"));
            j++;
        }
        j = 1;
        // 第 n 行的数据
        for (Map<String, Object> maps : mapList) {
            HSSFRow rowss = sheet.createRow(j);
            int i = 0;
            for (Map map : mapList) {
                //创建列
                rowss.createCell(i).setCellValue((String) maps.get(map.get("form_title")));
                List<String> a = new ArrayList<>();
                a.add("苗苗");
                a.add("苗苗"+ i);
                setSelect(sheet, rowss.getCell(i), a,false, "10085");
                i++;
            }
            j++;
        }

        //字节输出流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            outputStream.close();
        }
        //http请求头
        HttpHeaders httpHeaders = new HttpHeaders();
        String name = "sunwx.xls";
        String fileName = new String(name.getBytes("UTF-8"), "iso-8859-1");
        //设置内容属性
        httpHeaders.setContentDispositionFormData("attachment", fileName);
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        ResponseEntity<byte[]> filebyte = new ResponseEntity<byte[]>(outputStream.toByteArray(), httpHeaders, HttpStatus.CREATED);
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            outputStream.close();
        }


    }

    /**
     * 给单元格设置下拉框
     * @Param needTitle  是否需要设置提示,若不设置,则使用List中的第一条作为默认值
     */
    public static void setSelect(Sheet sheet, Cell cell, List<String> list, boolean needTitle, String title) {
        List<String> newList = null;
        // 普通写入操作
        if (needTitle) {
            cell.setCellValue(title);// 这是实验
            newList = list;
        } else {
            if (list == null || list.isEmpty()) {
                cell.setCellValue("");
            }else {
                cell.setCellValue(list.get(0));
            }
            if (list.size() > 1) {
                newList = list.subList(1, list.size());
            } else {
                newList = list;
            }
        }
    }
}
