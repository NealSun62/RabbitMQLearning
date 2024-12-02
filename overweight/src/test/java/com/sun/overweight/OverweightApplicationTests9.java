package com.sun.overweight;
import com.sun.overweight.common.utils.OfficeToPdfUtil;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextShape;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OverweightApplicationTests9 {


    public static void main(String[] args) {
        String pptFilePath = "E://input.pptx";
        String pdfFilePath = "E://output.pdf";

        try {
            convertFileToBytes(pptFilePath);
            System.out.println("Conversion completed successfully.");
        } catch (IOException e) {
            System.err.println("Error converting PPT to PDF: " + e.getMessage());
        }
    }

    public static void convertFileToBytes(String filePath) throws IOException {
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);

        byte[] bytes = new byte[(int) file.length()];
        fis.read(bytes);
        fis.close();

        // 写入另一个文件
        String outputFilePath = "E://output.pdf";
        FileOutputStream fos = new FileOutputStream(outputFilePath);
        fos.write(OfficeToPdfUtil.ppt2pdf(bytes));
        fos.close();

        System.out.println("File converted to bytes and written to " + outputFilePath + " successfully.");
    }



}