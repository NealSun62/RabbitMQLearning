package com.sun.overweight.common.utils;



import com.sun.overweight.enums.FileType;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

/**
 * @author gezc30201
 * @description 描述
 * @date 2020/5/8
 */
public class ExcelUtil {

    /**
     * description 描述
     *
     * @param in
     * @param fileEnd
     * @return org.apache.poi.ss.usermodel.Workbook
     * @author gezc30201
     * @date 2020/5/9
     */
    public static Workbook getWorkBook(InputStream in, String fileEnd) throws Exception {
        Workbook workbook = null;
        try {
            if (FileType.XLS.getFileSuffix().substring(1).equals(fileEnd)) {
                // 根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
                workbook = new HSSFWorkbook(in);
            } else if (FileType.XLSX.getFileSuffix().substring(1).equals(fileEnd)) {
                // 2007 及2007以上
                workbook = new XSSFWorkbook(in);
// 这是上一行代码的注释//2003
            }
        } catch (Exception e) {
//            log.error(ExceptionUtils.getTrace(e));
        }
        return workbook;
    }

    /**
     * description csv转excel
     *
     * @param bytes
     * @return byte[]
     * @author gezc30201
     * @date 2020/5/15
     */
    public static byte[] csvToExcel(byte[] bytes) throws IOException {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("Sheet1");
        BufferedReader r = null;
        ByteArrayOutputStream os = null;
        byte[] result = null;
        try {
            r = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(bytes)));
            int i = 0;
            while (true) {
                String ln = r.readLine();
                if (ln == null) {
                    break;
                }
                XSSFRow row = sheet.createRow((short) i++);
                int j = 0;

                String[] strs;
                if (ln.contains("|")) {
                    strs = ln.split("\\|");
                } else {
                    strs = ln.split(",");
                }
                for (String s : strs) {
                    XSSFCell cell = row.createCell((short) j++);
                    cell.setCellValue(s);
                }
            }
            os = new ByteArrayOutputStream();
            wb.write(os);
            result = os.toByteArray();
        } finally {
            if (r != null) {
                r.close();
            }
            if (os != null) {
                os.close();
            }
        }
        return result;
    }

}
