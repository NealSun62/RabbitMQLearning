package com.sun.overweight;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.PictureRenderData;
import com.sun.overweight.common.utils.TransUtil;
import com.sun.overweight.ramp.common.model.Users;
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
import java.util.concurrent.ConcurrentHashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OverweightApplicationTests8 {
    public static void main(String[] args) throws Exception {
        Users user = new Users();
        List<String> scrNumList = Arrays.asList("1052.XSHG","12556.XCFE");
        List<Integer> poolIdList =  Arrays.asList(1235,1561);
        Boolean workflowFlag = true;
        String adjustModeType = "q";
        user.setAdjustModeType(adjustModeType);
        user.setWorkflowFlag(workflowFlag);
        user.setPoolIdList(poolIdList);
        user.setScrNumList(scrNumList);
        Map<String, Object> map = new HashMap<>();
        map = TransUtil.beanToMap(user);
        System.out.println(JSON.toJSONString(map));
        // 
        if (scrNumList.contains(null)){
            System.out.println("包含");
        }else {
            System.out.println("nonono");
        }

        Users workFlowEventVO = JSONObject.parseObject("", new TypeReference<Users>(){});
        System.out.println(workFlowEventVO);

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