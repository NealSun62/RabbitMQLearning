package com.sun.overweight;

import com.sun.overweight.common.utils.StringUtil;
import com.sun.overweight.controller.UserController;
import com.sun.overweight.mapper.UserMapper;
import org.apache.poi.xwpf.usermodel.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;

import static com.sun.overweight.common.utils.DateUtil.dateToLocalDate;
import static com.sun.overweight.common.utils.DateUtil.parseDateStrInt;

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
    @Autowired
    public static UserMapper userMapper;

    @Test
    public static void main(String[] args) throws Exception {
        int total = 0;
        File f = new File("E:" + File.separator + "test.docx");
        InputStream in = new FileInputStream(f);
        XWPFDocument xwpfDocument = new XWPFDocument(in);
        List<XWPFTable> xwpfTables = xwpfDocument.getTables();
        XWPFTable xwpfTable = xwpfTables.get(0);
        List<XWPFTableRow> xwpfTableRows = xwpfTable.getRows();
        for (XWPFTableRow xwpfTableRow : xwpfTableRows) {
            List<XWPFTableCell> xwpfTableCells = xwpfTableRow.getTableCells();
            for (int i = 0; i < xwpfTableCells.size(); i++) {
                if (xwpfTableCells.get(i).getText().contains("评估人员")) {
                    List<XWPFParagraph> xwpfParagraphs = xwpfTableCells.get(i + 1).getParagraphs();
                    if (xwpfParagraphs == null) {
                        System.out.println("xwpfParagraph is null");
                    }
                    for (XWPFParagraph xwpfParagraph : xwpfParagraphs) {
                        List<XWPFRun> xwpfRunList = xwpfParagraph.getRuns();
                        if (xwpfRunList == null) {
                            System.out.println("xwpfRunList is null");
                        } else {
                            System.out.println("xwpfRunList is not null");
                        }
                        for (XWPFRun xwpfRun : xwpfRunList) {
                            List<XWPFPicture> xwpfPictureList = xwpfRun.getEmbeddedPictures();
                            if (xwpfPictureList == null) {
                                System.out.println("xwpfPictureList is null");
                            } else {
                                System.out.println("xwpfPictureList is not null");
                            }
                            if (xwpfPictureList.size() > 0) {
                                for (XWPFPicture xwpfPicture : xwpfPictureList) {
                                    xwpfPicture.getPictureData().getData();
                                    String fileName = xwpfPicture.getPictureData().getFileName().trim();
                                    if (StringUtil.isNotEmpty(fileName)) {
                                        total++;
                                        System.out.println("fileName" + fileName);
                                    } else {
                                        System.out.println("No fileName" + fileName);
                                    }
                                }
                            } else {
                                System.out.println("没图片");
                            }
                        }
                    }

                }
                if (xwpfTableCells.get(i).getText().contains("评审人员")) {
                    List<XWPFParagraph> xwpfParagraphs = xwpfTableCells.get(i + 1).getParagraphs();
                    if (xwpfParagraphs == null) {
                        System.out.println("xwpfParagraph is null");
                    }
                    for (XWPFParagraph xwpfParagraph : xwpfParagraphs) {
                        List<XWPFRun> xwpfRunList = xwpfParagraph.getRuns();
                        if (xwpfRunList == null) {
                            System.out.println("xwpfRunList is null");
                        } else {
                            System.out.println("xwpfRunList is not null");
                        }
                        for (XWPFRun xwpfRun : xwpfRunList) {
                            List<XWPFPicture> xwpfPictureList = xwpfRun.getEmbeddedPictures();
                            if (xwpfPictureList == null) {
                                System.out.println("xwpfPictureList is null");
                            } else {
                                System.out.println("xwpfPictureList is not null");
                            }
                            if (xwpfPictureList.size() > 0) {
                                for (XWPFPicture xwpfPicture : xwpfPictureList) {
                                    xwpfPicture.getPictureData().getData();
                                    String fileName = xwpfPicture.getPictureData().getFileName().trim();
                                    if (StringUtil.isNotEmpty(fileName)) {
                                        total++;
                                        System.out.println("fileName" + fileName);
                                    } else {
                                        System.out.println("No fileName" + fileName);
                                    }
                                }
                            } else {
                                System.out.println("没图片");
                            }
                        }
                    }

                }
            }
        }
        System.out.println("total==" + total);

    }
}
