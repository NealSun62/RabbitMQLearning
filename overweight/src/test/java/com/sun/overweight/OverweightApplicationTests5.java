package com.sun.overweight;

import com.sun.overweight.common.utils.CommUtil;
import com.sun.overweight.common.utils.DateUtil;
import com.sun.overweight.entity.RankVo;
import org.apache.commons.collections.CollectionUtils;
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
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static com.sun.overweight.common.utils.JsonUtil.toJson;

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
        List<RankVo> inputList = new ArrayList<>();
        RankVo rankVo1 = RankVo.builder().code("code1").oriValue(new BigDecimal("0.2")).value(new BigDecimal("1")).build();
        RankVo rankVo2 = RankVo.builder().code("code2").oriValue(new BigDecimal("0.2")).value(new BigDecimal("2")).build();
        RankVo rankVo3 = RankVo.builder().code("code3").oriValue(new BigDecimal("0.2")).value(new BigDecimal("3")).build();
        RankVo rankVo4 = RankVo.builder().code("code4").oriValue(new BigDecimal("0.2")).value(new BigDecimal("4")).build();
        RankVo rankVo9 = RankVo.builder().code("code9").oriValue(null).value(null).build();
        RankVo rankVo5 = RankVo.builder().code("code5").oriValue(new BigDecimal("0.2")).value(new BigDecimal("5")).build();
        RankVo rankVo8 = RankVo.builder().code("code8").oriValue(new BigDecimal("0.2")).value(new BigDecimal("5")).build();
        RankVo rankVo6 = RankVo.builder().code("code6").oriValue(new BigDecimal("0.2")).value(new BigDecimal("6")).build();
        RankVo rankVo7 = RankVo.builder().code("code7").oriValue(new BigDecimal("0.2")).value(new BigDecimal("7")).build();
        inputList.add(rankVo1);
        inputList.add(rankVo2);
        inputList.add(rankVo3);
        inputList.add(rankVo4);
        inputList.add(rankVo5);
        inputList.add(rankVo6);
        inputList.add(rankVo7);
        inputList.add(rankVo8);
        inputList= calRank(inputList, "1");
        System.out.println(toJson(inputList));

    }

    public static  List<RankVo> calRank(List<RankVo> inputList, String dir) {
        if (CollectionUtils.isEmpty(inputList)) {
            return new ArrayList<>();
        }
        // 正方向：如收益率，值越大，排名越靠前，rankNum越小
        if ("1".equals(dir)) {
            // 值从大到小-nulllast
            inputList.sort(Comparator.nullsLast(Comparator.comparing(RankVo::getValue,
                    Comparator.nullsFirst(BigDecimal::compareTo)).reversed()));
        } else {
            // 值从小到大-nulllast
            inputList.sort(Comparator.nullsLast(Comparator.comparing(RankVo::getValue,
                    Comparator.nullsLast(BigDecimal::compareTo))));
        }
        List<RankVo> sortInput = new ArrayList<>(inputList);
        BigDecimal flagNum = null;
        int rankNum = 1;
        int sameCount = 0;
        for (int i = 0; i < sortInput.size(); i++) {
            RankVo rankVo = sortInput.get(i);
            BigDecimal value = rankVo.getValue();
            if (value == null) {
                rankVo.setRankNum(null);
                continue;
            }
            if (i == 0) {
                flagNum = value;
            } else {
                if (flagNum != null && flagNum.compareTo(value) != 0) {
                    flagNum = value;
                    rankNum = rankNum + 1 + sameCount;
                    sameCount = 0;
                } else {
                    flagNum = value;
                    sameCount = sameCount + 1;
                }
            }
            rankVo.setRankNum(rankNum);
        }
        return sortInput;
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
