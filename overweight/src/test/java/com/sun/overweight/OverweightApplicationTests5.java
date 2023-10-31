package com.sun.overweight;

import com.google.common.collect.Lists;
import com.sun.overweight.common.utils.CommUtil;
import com.sun.overweight.common.utils.DateUtil;
import com.sun.overweight.controller.UserController;
import org.junit.Test;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.util.Units;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDocument1;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTParagraph;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblCell;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPicture;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTInline;
import org.w3c.dom.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.util.Units;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 * @param
 * @author neal
 * @return
 * @date 2020/01/01
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OverweightApplicationTests5 {
    public static void main(String[] args) throws Exception {
        String templatePath = "template_word_path"; // 模板文件路径
        String outputPath = "output_word_path"; // 输出文件路径
        String imagePath = "image_path"; // 图片文件路径
        String[] data = {"张三", "男", "25"}; // 需要填充的数据

        fillWordTemplate(templatePath, outputPath, imagePath, data);
    }

    public static void fillWordTemplate(String templatePath, String outputPath, String imagePath, String[] data) throws Exception {
        // 读取模板文件
        FileInputStream fis = new FileInputStream(new File(templatePath));
        XWPFDocument document = new XWPFDocument(fis);
        fis.close();

        // 获取正文内容
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        for (XWPFParagraph paragraph : paragraphs) {
            List<XWPFRun> runs = paragraph.getRuns();
            for (XWPFRun run : runs) {
                String text = run.getText(0);
                if (text != null) {
                    for (int i = 0; i < data.length; i++) {
                        text = text.replace("{" + i + "}", data[i]);
                    }
                    run.setText(text, 0); // 更新文本内容
                }
            }
        }

        // 插入图片到指定位置（这里以第一个表格为例）
        List<XWPFTable> tables = document.getTables();
        if (!tables.isEmpty()) {
            XWPFTable table = tables.get(0); // 获取第一个表格
            int rowIndex = 1; // 插入图片的行索引（从1开始）
            int colIndex = 1; // 插入图片的列索引（从1开始）
            insertImageToTable(table, rowIndex, colIndex, imagePath); // 插入图片到表格中指定位置的方法，需要自定义实现该方法，参考下面的代码示例
        } else {
            System.out.println("未找到表格");
        }

        // 保存输出文件
        FileOutputStream fos = new FileOutputStream(new File(outputPath));
        document.write(fos); // 将修改后的文档写入输出文件流中，并关闭输出流和文档对象
        fos.close();
        document.close(); // 关闭文档对象，释放资源
    }
}
