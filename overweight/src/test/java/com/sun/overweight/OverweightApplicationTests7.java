package com.sun.overweight;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.util.BytePictureUtils;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OverweightApplicationTests7 {
    public static void main(String[] args) throws Exception {
        // base64转落地本地图片
        String imagePath = "E:\\tmp.jpg";
        String base64Image = null;
        try {
            base64Image = convertImageToBase64(imagePath);
            System.out.println("Base64编码的图片： " + base64Image);
        } catch (IOException e) {
            System.err.println("转换图片失败： " + e.getMessage());
        }
        saveBase64StringAsImage(base64Image, "E:\\output.png");
        Map<String, Object> map = new HashMap<>();
        map.put("name", "tony");
        map.put("age", "18");
        // 读取本地磁盘图片
        map.put("weChatPicture", new PictureRenderData(100, 100, new File("E:\\output.png")));


        // 通过url读取网络图片
        // https://blog.csdn.net/dava_zhang/article/details/122214736

        File file = new File("E:\\tmpdoc.docx");//看tmpl目录
        XWPFTemplate template = XWPFTemplate.compile(file).render(map);
        String name = "E:\\输出自我介绍" + System.currentTimeMillis() + ".docx";
        FileOutputStream out = new FileOutputStream(new File(name));
        template.write(out);
        out.flush();
        out.close();
        template.close();

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