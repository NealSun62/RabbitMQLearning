package com.sun.overweight;

import com.sun.overweight.common.utils.CommUtil;
import com.sun.overweight.common.utils.DateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.Units;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.junit.runner.RunWith;
import org.openxmlformats.schemas.drawingml.x2006.main.*;
import org.openxmlformats.schemas.drawingml.x2006.picture.*;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.apache.poi.xwpf.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.*;
import java.math.BigInteger;
import java.util.Date;

/**
 * @param
 * @author neal
 * @return
 * @date 2020/01/01
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OverweightApplicationTests5 {
    public static void main(String[] args) {
        String time = CommUtil.uuid();
        String templateFilePath = "E:\\tmp1.docx";
        String outputFilePath = "E:\\" + time + ".docx";
        String imagePath = "E:\\2.jpg"; // 替换成你实际的图片路径

        try (InputStream inputStream = new FileInputStream(templateFilePath);
             OutputStream outputStream = new FileOutputStream(outputFilePath)) {
            XWPFDocument document = new XWPFDocument(inputStream);

            // 替换文档中的标记
            replacePlaceholder(document, "#{insName}", "万科企业股份有限公司"); // 替换成你实际的占位符和相关内容

            // 插入图片到指定位置
            insertImage(document, "#{pic}", imagePath); // 替换成你实际的图片占位符

            // 保存文档到输出文件
            document.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void replacePlaceholder(XWPFDocument document, String placeholder, String replacement) {
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            String text = paragraph.getText();
            if (text.contains(placeholder)) {
                for (XWPFRun run : paragraph.getRuns()) {
                    String runText = run.getText(0);
                    if (runText != null && runText.contains(placeholder)) {
                        runText = runText.replace(placeholder, replacement);
                        run.setText(runText, 0);
                    }
                }
            }
        }
    }

    private static void insertImage(XWPFDocument document, String placeholder, String imagePath) {
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            for (XWPFRun run : paragraph.getRuns()) {
                String text = run.getText(0);
                if (text != null && text.contains(placeholder)) {
                    text = text.replace(placeholder, "");
                    run.setText(text, 0);
                    try (InputStream imageStream = new FileInputStream(imagePath)) {
                        run.addBreak();
                        run.addPicture(imageStream, XWPFDocument.PICTURE_TYPE_PNG, imagePath, Units.toEMU(200), Units.toEMU(200)); // 调整图片尺寸
                    } catch (IOException | InvalidFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
