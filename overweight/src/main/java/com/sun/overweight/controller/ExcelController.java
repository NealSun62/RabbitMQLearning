package com.sun.overweight.controller;

import com.alibaba.fastjson.JSONArray;
import com.sun.overweight.common.utils.excel.ExcelUtils;
import com.sun.overweight.ramp.common.model.Users;
import com.sun.overweight.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author sunwx33102
 * @description
 * @date 2021-05-14 14:48
 */
@Controller
@RequestMapping(value = "/excel")
public class ExcelController {

    @GetMapping("dropList")
    @ResponseBody
    @ApiOperation(value = "info", notes = "getInfo")
    public static void dropList(HttpServletResponse response) throws IOException {
        List<Object> head = Arrays.asList("主体名称","定性指标1","定量指标2","定性指标2","定性没映射指标3");
        String tempFileName = "insMaintTmpl.xlsx";
        List<Object> infos = new ArrayList<>();
        infos.add("万科企业股份");
        infos.add("国企");
        infos.add("-1235.25");
        infos.add("一公里以内");
        infos.add("游泳");
        List<Object> infos2 = new ArrayList<>();
        infos2.add("恒生电子");
        infos2.add("名企");
        infos2.add("2351536156415451.5131532534");
        infos2.add("两公里以内");
        infos2.add("跳舞");

        List<List<Object>> sheetDataList = new ArrayList<>();
        sheetDataList.add(head);
        sheetDataList.add(infos);
        sheetDataList.add(infos2);

        Map<Integer, List<String>> selectMap = new HashMap<>();
        selectMap.put(1, Arrays.asList("国企","一般企业","名企"));
        selectMap.put(3, Arrays.asList("两公里以内","三公里以外","一公里以内"));
        String fileName = "机构指标维护导出"+ System.currentTimeMillis();
        ExcelUtils.export(response, null, fileName,"机构指标数据维护", sheetDataList, selectMap, tempFileName);

    }

    @PostMapping("/import")
    public JSONArray importUser(@RequestPart("file") MultipartFile file) throws Exception {
        JSONArray array = ExcelUtils.readMultipartFile(file);
        System.out.println("导入数据为:" + array);
        return array;
    }

    @GetMapping("getInfo3")
    @ResponseBody
    @ApiOperation(value = "info", notes = "getINfo")
    public static void writeExcels3(HttpServletResponse response) throws IOException {
        try {
            String[] title = {"届别", "学部", "年级", "班级名称", "班级代码", "班级排序"};
            // 1.创建Excel工作薄对象
            HSSFWorkbook wb = new HSSFWorkbook();
            // 2.创建Excel工作表对象
            HSSFSheet sheet = wb.createSheet("班级信息导入范本");
            // 3.创建Excel工作表的行
            HSSFRow row = sheet.createRow(0);
            for (int i = 0; i < title.length; i++) {
                //6.设置Excel工作表的值
                row.createCell(i).setCellValue(title[i]);
            }
            List<String> periodList = new ArrayList<>();
            periodList.add("苗苗");
            periodList.add("NEAL");
            // 表格下拉【届别】
            if (periodList.size() > 0) {
                String[] periodArray = periodList.toArray(new String[periodList.size()]);
                AddComboBox("无所谓的", wb, sheet, 2, 0, periodArray);
            }
            // 设置sheet名称和单元格内容
            wb.setSheetName(0, "班级信息导入范本");
            //字节输出流
            OutputStream out = response.getOutputStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            try {
                wb.write(outputStream);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                outputStream.close();
            }
            //http请求头
            String name = System.currentTimeMillis() +"sunwx.xlsx";
            response.setHeader("Content-Disposition",
                    "inline;filename=" + new String(name.getBytes("utf-8"),"iso8859-1"));
            wb.write(outputStream);
            out.write(outputStream.toByteArray());
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                outputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }/**
     * 下载的Excel表格中
     * 列表下拉选择框
     *
     * @param sheetName
     * @param workbook
     * @param sheet
     * @param rowIndex
     * @param colIndex
     * @param list
     */
    public static void AddComboBox(String sheetName, HSSFWorkbook workbook, HSSFSheet sheet, int rowIndex, int colIndex, String[] list) {
        if (list.length > 10) {
            //数据源sheet页不显示
            HSSFSheet hidden = workbook.createSheet(sheetName);
            HSSFRow row = null;
            HSSFCell cell = null;
            for (int i = 0, length = list.length; i < length; i++) {
                row = hidden.createRow(i);
                cell = row.createCell(0);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                cell.setCellValue(list[i]);
            }
            Name namedCell = workbook.createName();
            namedCell.setNameName(sheetName);
            String exp = sheetName + "!$A$1:$A$" + String.valueOf(list.length);
            namedCell.setRefersToFormula(exp);
            DVConstraint dvConstraint = DVConstraint.createFormulaListConstraint(sheetName);
            CellRangeAddressList addressList = new CellRangeAddressList(1, 100000, colIndex, colIndex);
            HSSFDataValidation validation = new HSSFDataValidation(addressList, dvConstraint);
            sheet.addValidationData(validation);
        } else {
            CellRangeAddressList regions = new CellRangeAddressList(1, 100000, colIndex, colIndex);
            //生成下拉框内容
            DVConstraint constraint = DVConstraint.createExplicitListConstraint(list);
            //绑定下拉框和作用区域
            HSSFDataValidation data_validation = new HSSFDataValidation(regions, constraint);
            //对sheet页生效
            sheet.addValidationData(data_validation);
        }
    }
}