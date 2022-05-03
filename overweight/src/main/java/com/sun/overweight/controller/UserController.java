package com.sun.overweight.controller;

import com.google.common.collect.Lists;
import com.sun.overweight.common.utils.BeanUtil;
import com.sun.overweight.entity.EasyExcelParams;
import com.sun.overweight.ramp.common.model.*;
import com.sun.overweight.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author sunwx33102
 * @description
 * @date 2021-05-14 14:48
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Resource(name = "userService")
    UserService userService;

    @GetMapping("getUser1")
    @ResponseBody
    @ApiOperation(value = "用户", notes = "用户")
    public Users getUser1(@RequestParam("name") String name) {
        Users users = userService.findDs1User(name);
        return users;
    }

    @GetMapping("getUser2")
    @ResponseBody
    @ApiOperation(value = "用户", notes = "用户")
    public Users getUser2(@RequestParam("name") String name) {
        Users users = userService.findDs2User(name);
        return users;

    }

    //    @GetMapping("getInfo")
//    @ResponseBody
//    @ApiOperation(value = "info", notes = "getINfo")
//    public List<CrdmBaseComptCfgDetailResponseVo> getInfo() {
//        List<CrdmBaseComptCfgDetailTypeVo> list = userService.getInfo();
//        List<CrdmBaseComptCfgDetailResponseVo> crdmBaseComptCfgDetailResponseVos = new ArrayList<>();
//        Map<String, List<CrdmBaseComptCfgDetailTypeVo>> map = list.parallelStream().collect(Collectors.groupingBy(CrdmBaseComptCfgDetailTypeVo::getAreaId));
//        Set<Map.Entry<String, List<CrdmBaseComptCfgDetailTypeVo>>> entries = map.entrySet();
//        for (Map.Entry entry : entries) {
//            List<CrdmBaseComptCfgDetailTypeVo> list1 = (List<CrdmBaseComptCfgDetailTypeVo>) entry.getValue();
//            Map<String, List<CrdmBaseComptCfgDetailTypeVo>> map2 = list1.parallelStream().collect(Collectors.groupingBy(CrdmBaseComptCfgDetailTypeVo::getLabelName));
//            Set<Map.Entry<String, List<CrdmBaseComptCfgDetailTypeVo>>> entries2 = map2.entrySet();
//            for (Map.Entry entry2 : entries2) {
//                List<CrdmBaseCompTypeVo> list3 = new ArrayList<>();
//                CrdmBaseComptCfgDetailResponseVo crdmBaseComptCfgDetailResponseVo = new CrdmBaseComptCfgDetailResponseVo();
//                List<CrdmBaseComptCfgDetailTypeVo> list2 = (List<CrdmBaseComptCfgDetailTypeVo>) entry2.getValue();
//                CrdmBaseComptCfgDetailTypeVo tempVo = list2.get(0);
//                BeanUtil.copyProperties(tempVo, crdmBaseComptCfgDetailResponseVo);
//                list2.forEach(p -> {
//                    CrdmBaseCompTypeVo vo = new CrdmBaseCompTypeVo();
//                    if (p.getTypeId() != null) {
//                        vo.setTypeId(p.getTypeId());
//                        vo.setValue(p.getValue());
//                        list3.add(vo);
//                    }
//                });
//                crdmBaseComptCfgDetailResponseVo.setLabelTypeList(list3);
//                crdmBaseComptCfgDetailResponseVos.add(crdmBaseComptCfgDetailResponseVo);
//            }
//        }
//        return crdmBaseComptCfgDetailResponseVos;
//
//    }
//
    @GetMapping("getInfo2")
    @ResponseBody
    @ApiOperation(value = "info", notes = "getINfo")
    public static void writeExcels(HttpServletResponse response) throws IOException {
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
        map0.put("form_title", " ");
        map0.put(" ","指标1");
        map0.put("2020","2020指标1的值");
        map0.put("2021","2021指标1的值");
        Map map1 = new HashMap();
        map1.put("form_title", "2020");
        map1.put(" ","指标2");
        map1.put("2020","2020指标2的值");
        map1.put("2021","2021指标2的值");
        Map map2 = new HashMap();
        map2.put("form_title", "2021");
        map2.put(" ", "指标3");
        map2.put("2020","2020指标3的值");
        map2.put("2021","2021指标3的值");
        mapList.add(map0);
        mapList.add(map1);
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
                i++;
            }
            j++;
        }

        //字节输出流
        OutputStream out = response.getOutputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            outputStream.close();
        }
        //http请求头
        String name = System.currentTimeMillis() +"sunwx.xlsx";
        response.setHeader("Content-Disposition",
                "inline;filename=" + new String(name.getBytes("utf-8"),"iso8859-1"));
        workbook.write(outputStream);
        out.write(outputStream.toByteArray());
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            outputStream.close();
        }
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