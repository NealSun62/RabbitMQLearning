package com.sun.overweight;

import com.sun.overweight.controller.UserController;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        HttpServletRequest request; HttpServletResponse response;
        //创建工作薄
        HSSFWorkbook wb = new HSSFWorkbook();
        // 创建sheet
        Sheet sheet = wb.createSheet("当月");
        //表头字体
        Font headerFont = wb.createFont();
        headerFont.setFontName("宋体");
        headerFont.setFontHeightInPoints((short) 18);
        headerFont.setBold(true);
        headerFont.setColor(Font.COLOR_NORMAL);
        //正文字体
        Font contextFont = wb.createFont();
        contextFont.setFontName("宋体");
        contextFont.setFontHeightInPoints((short) 12);
        headerFont.setBold(true);
        //表头样式，左右上下居中
        CellStyle headerStyle = wb.createCellStyle();
        headerStyle.setFont(headerFont);
        // 左右居中
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        // 上下居中
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setLocked(true);
        // 自动换行
        headerStyle.setWrapText(false);
        //单元格样式，左右上下居中 边框
        CellStyle commonStyle = wb.createCellStyle();
        commonStyle.setFont(contextFont);
        // 左右居中
        commonStyle.setAlignment(HorizontalAlignment.CENTER);
        // 上下居中
        commonStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        commonStyle.setLocked(true);
        // 自动换行
        commonStyle.setWrapText(false);
        //单元格样式，左右上下居中 边框
        CellStyle commonWrapStyle = wb.createCellStyle();
        commonWrapStyle.setFont(contextFont);
        //单元格样式，竖向 边框
        CellStyle verticalStyle = wb.createCellStyle();
        verticalStyle.setFont(contextFont);
        CellStyle commonStyleNoBorder = wb.createCellStyle();
        commonStyleNoBorder.setFont(contextFont);
        commonStyleNoBorder.setLocked(true);
        // 自动换行
        commonStyleNoBorder.setWrapText(false);
        CellStyle alignLeftStyle = wb.createCellStyle();
        alignLeftStyle.setFont(contextFont);
        alignLeftStyle.setLocked(true);
        // 自动换行
        alignLeftStyle.setWrapText(false);
        //单元格样式，左对齐 无边框
        CellStyle alignLeftNoBorderStyle = wb.createCellStyle();
        alignLeftNoBorderStyle.setFont(contextFont);
        alignLeftNoBorderStyle.setLocked(true);
        // 自动换行
        alignLeftNoBorderStyle.setWrapText(false);
        //单元格样式，右对齐
        CellStyle alignRightStyle = wb.createCellStyle();
        alignRightStyle.setFont(contextFont);
        alignRightStyle.setLocked(true);
        // 自动换行
        alignRightStyle.setWrapText(false);
        // 行号
        int rowNum = 0;
        //设置列宽
        for (int i = 0; i < 11; i++) {
            sheet.setColumnWidth(i, 3000);
        }

        //第一行
        Row r0 = sheet.createRow(rowNum++);
        r0.setHeight((short) 800);
        Cell c00 = r0.createCell(0);
        c00.setCellValue("表二：");
        c00.setCellStyle(alignLeftNoBorderStyle);
        //合并单元格
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));
        //第二行城市对象综合统计表
        Row r1= sheet.createRow(rowNum++);
        r1.setHeight((short) 800);
        Cell r10 = r1.createCell(0);
        r10.setCellValue("城市对象管理情况统计表");
        r10.setCellStyle(headerStyle);
        //合并单元格
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 8));
        //第三行
        Row r2 = sheet.createRow(rowNum++);
        r2.setHeight((short) 800);
        Cell r20 = r2.createCell(0);
        r20.setCellValue("填表单位：XXX街道");
        r20.setCellStyle(alignLeftNoBorderStyle);
        //合并单元格
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 4));
        //第四行
        Row r3 = sheet.createRow(rowNum++);
        r3.setHeight((short) 700);
        String[] rowSecond = {"地  区", "对象情况1", "","对象情况2","" ,"调增情况3","","情况4",""};

        for (int i = 0; i < rowSecond.length; i++) {
            Cell tempCell = r3.createCell(i);
            tempCell.setCellValue(rowSecond[i]);
            tempCell.setCellStyle(commonStyle);
        }
        //第5行
        Row r4 = sheet.createRow(rowNum++);
        String[] rowSecond5 = {"1", "2", "3","4","5","6","7","8"};
        for (int i = 0; i < rowSecond5.length; i++) {
            Cell tempCell = r4.createCell(i+1);
            tempCell.setCellValue(rowSecond5[i]);
            tempCell.setCellStyle(commonStyle);
        }
        //合并单元格
        sheet.addMergedRegion(new CellRangeAddress(3, 4, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 1, 2));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 3, 4));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 5, 6));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 7, 8));
        //查询数据
        List<Map<String, Object>> dataList = new ArrayList<>();
        //统计合计数据
        Map<String,Object> mapTotal = new HashMap<>();
        for (Map<String, Object> map : dataList) {
            Row tempRow = sheet.createRow(rowNum++);
            //列表数据
            Cell tempCell0 = tempRow.createCell(0);
            String sqmc = (String) map.get("XXX");
            tempCell0.setCellValue(sqmc);
            tempCell0.setCellStyle(commonStyle);
        }

        //字节输出流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            wb.write(outputStream);
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
}
