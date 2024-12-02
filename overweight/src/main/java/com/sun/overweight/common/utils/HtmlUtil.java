//package com.sun.overweight.common.utils;
//
//import com.aspose.slides.Presentation;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.InputStream;
//
///**
// * @author sunwx33102
// * @description
// * @date 2024-12-02 14:24
// */
//public class HtmlUtil {
//
//    @Value("${upload.url.config}")//获取yml中config的值（路径），即字体库的路径，如C:/fonts，用于转换ppt中的文字字体为pdf字体
//    private String config;
//
//    private static String configUrl;
//
//    private static InputStream license;
//    private Document doc;
//
//    /**
//     *
//     */
//    @PostConstruct
//    public void getApiToken() {
//        configUrl = this.config;
//    }
//
//    /**
//     *
//     * @return
//     */
//    private static String getConfigUrl() {
//        return configUrl;
//    }
//
//    /**
//     * 获取该工具类的许可证，没有许可证会导致转换后的文档有水印
//     * @return
//     */
//    private static boolean getLicense() {
//        boolean result = false;
//        try {
//            String licenseFile = getConfigUrl() + File.separator + "license.xml";//license.xml文件地址，如C:/license.xml
//            System.out.println(licenseFile);
//            //创建输入流
//            license = new FileInputStream(licenseFile);
//            License aposeLic = new License();
//            aposeLic.setLicense(license);
//            result = true;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//
//    /**
//     *
//     * @param in
//     * @param path
//     */
//    public static void ppt2PDF(InputStream in, String path) {
//        if (!PPTUtil.getLicenseForPPT()) {
//            return;
//        }
//        // 字体设置
//        String fontPath = getConfigUrl() + File.separator + "fonts";
//        FontSettings.getDefaultInstance().setFontsFolder(fontPath, true);
//        try {
//            long old = System.currentTimeMillis();//返回当前计算机时间，表达格式为毫秒
//            Presentation ppt= new Presentation(in);
//            FileOutputStream fileOS = new FileOutputStream(new File(path));//创建输出流
//            // 用输出流往pdf文件里写东西
//            ppt.save(fileOS, com.aspose.slides.SaveFormat.Pdf);
//            long now = System.currentTimeMillis();
//            System.out.println("ppt转PDF共耗时：" + ((now - old) / 1000.0) + "秒\n\n");
//            fileOS.close();//关闭输出流
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//}