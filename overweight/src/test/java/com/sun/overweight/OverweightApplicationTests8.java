package com.sun.overweight;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.PictureRenderData;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OverweightApplicationTests8 {
    public static void main(String[] args) throws Exception {
        // base64转落地本地图片
        List<String> a = new ArrayList<>();
        a.add("ceshi1");
        a.add("ceshi2");
        a.add("ceshi3");
        a.forEach(p -> {
                    if (p.contains("2")) {
                        return;
                    }
            System.out.println(p);
                }
        );

    }

    public static String convertImageToBase64(String imagePath) throws IOException {
        File imageFile = new File(imagePath);
        byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    public static void saveBase64StringAsImage(String base64String, String outputPath) {
        try {
            // 解码base64字符串
            byte[] imageBytes = Base64.getDecoder().decode(base64String);

            // 将字节数组写入到图片文件中
            File outputFile = new File(outputPath);
            ImageIO.write(ImageIO.read(new ByteArrayInputStream(imageBytes)), "png", outputFile);

            // 打开图片文件
//            if (Desktop.isDesktopSupported()) {
//                Desktop.getDesktop().open(outputFile);
//            } else {
//                System.err.println("桌面不支持");
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}