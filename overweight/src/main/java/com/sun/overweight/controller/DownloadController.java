//package com.sun.overweight.controller;/*
//
//
///**
// * 功能说明: Description(用一句话描述该文件做什么)
// *
// * @author: zhangli25087 zhangli25087@hundsun.com <br>
// * @date: 2019/6/1 16:43 <br>
// * 功能描述: 写明作用，调用方式，使用场景，以及特殊情况<br>
// */
//
//import com.sun.overweight.enums.FileType;
//import com.sun.overweight.common.utils.ExcelUtil;
//import com.sun.overweight.common.utils.OfficeToPdfUtil;
//import com.sun.overweight.common.utils.PreviewUtil;
//import io.swagger.annotations.ApiOperation;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.*;
//import java.util.Arrays;
//import java.util.List;
//
///**
// * @author
// * @date
// */
//@RestController
//@RequestMapping(value = "/download")
//public class DownloadController {
//
//    Log log = LogFactory.getLog(DownloadController.class);
//
//    /**
//     * 下载速度 1KB
//     */
//    private static final Integer DOWNLOAD_LENS = 1 << 10;
//
//    /**
//     * 方法简介: 预览附件
//     *
//     * @param response :
//     * @return
//     * @author: zhangli25087 zhangli25087@hundsun.com
//     * @date: 2019/6/1 16:50 <br>
//     * @describe:
//     */
//    @GetMapping("/previewAttach")
//    @ApiOperation(value = "预览", notes = "预览")
//    public void previewAttach(HttpServletResponse response) {
//
//        String[] pictureSuffix = {"gif", "png", "jpg", "jpeg", "bmp"};
////        File file = new File("E:\\1.docx");
////        File file = new File("E:\\JAVA核心知识点整理.pdf");
//        File file = new File("E:\\非标评级要素字段定义V4-20200828.xlsx");
////        File file = new File("E:\\temp.doc");
////        File file = new File("E:\\qr3.xls");
//        String fileName = file.getName().toLowerCase();
//        String fileSuffix = fileName.substring(fileName.lastIndexOf("."));
//        InputStream is = null;
//        try {
//            is = file2InputStream(file);
//        } catch (Exception e) {
//            System.out.println("file2inputStream error");
//        }
//        List<String> officeSuffixPoint = Arrays.asList(FileType.OFFICE_SUFFIX_POINT);
//
//
//        byte[] bytes = new byte[1024 * 5];
//        try {
//            bytes = inputStream2byte(is);
//        } catch (Exception e) {
//            System.out.println("inputStream2byte error");
//        }
//        if (officeSuffixPoint.contains(fileSuffix)) {
//            if (bytes.length > FileType.FILE_SIZE_MAX) {
//                System.out.println("附件超过10M，请下载查看");
//            }
//        }
//        if (FileType.DOC.getFileSuffix().equals(fileSuffix) ||
//                FileType.DOCX.getFileSuffix().equals(fileSuffix) ||
//                FileType.RTF.getFileSuffix().equals(fileSuffix) ||
//                FileType.DOCM.getFileSuffix().equals(fileSuffix)) {
//            try {
//                bytes = OfficeToPdfUtil.word2pdf(bytes);
//            } catch (Exception e) {
//                //log.error(ExceptionUtils.getTrace(e));
//                //throw new BaseBizException("-1", "word转换pdf失败");
//            }
//        } else if (FileType.XLS.getFileSuffix().equals(fileSuffix) ||
//                FileType.XLSX.getFileSuffix().equals(fileSuffix) ||
//                FileType.XLSM.getFileSuffix().equals(fileSuffix)) {
//            try {
//                bytes = OfficeToPdfUtil.excel2pdf(bytes);
//            } catch (IOException e) {
//                //log.error(ExceptionUtils.getTrace(e));
//                //throw new BaseBizException("-1", "excel转换pdf失败");
//            }
//        } else if (FileType.PPT.getFileSuffix().equals(fileSuffix) ||
//                FileType.PPTX.getFileSuffix().equals(fileSuffix)) {
//            try {
//                bytes = OfficeToPdfUtil.ppt2pdf(bytes);
//            } catch (IOException e) {
//                //log.error(ExceptionUtils.getTrace(e));
//                //throw new BaseBizException("-1", "ppt转换pdf失败");
//            }
//        } else if (FileType.CSV.getFileSuffix().equals(fileSuffix)) {
//            try {
//                bytes = OfficeToPdfUtil.excel2pdf(ExcelUtil.csvToExcel(bytes));
//            } catch (IOException e) {
//                //log.error(ExceptionUtils.getTrace(e));
//                //throw new BaseBizException("-1", "csv转换pdf失败");
//            }
//        } else if (FileType.TEX.getFileSuffix().equals(fileSuffix)) {
//            PreviewUtil.txtPreview(response, bytes);
//            return;
//        } else if (Arrays.asList(pictureSuffix).contains(fileSuffix.substring(1))) {
//            PreviewUtil.imgPreview(response, bytes);
//            return;
//        }
//
//
//        OutputStream os = null;
//        try {
//            response.setHeader("content-length", String.valueOf(bytes.length));
//            // 预览
//            response.setContentType("application/pdf;charset=utf-8");
//            response.setHeader("content-disposition", "inline;filename=" +
//                    java.net.URLEncoder.encode(fileName == null ? "" : fileName, "UTF-8"));
//            os = response.getOutputStream();
//            // os.write(fileName.getBytes(),0,fileName.getBytes().length);
//            int lens = bytes.length;
//            int flag = 0;
//            while (lens > 0) {
//                os.write(Arrays.copyOfRange(bytes,
//                        flag * DOWNLOAD_LENS, (lens > DOWNLOAD_LENS) ? (flag + 1) * DOWNLOAD_LENS : (flag * DOWNLOAD_LENS + lens)));
//                flag++;
////                log.info(flag);
//                lens -= DOWNLOAD_LENS;
//            }
//            System.out.println("结束");
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (os != null) {
//                try {
//                    os.flush();
//                    os.close();
//                } catch (IOException e) {
//                }
//            }
//            System.gc();
//        }
//
//    }
//
//    //读取文件到byte[]
//    private static byte[] getFileBytes(String file) {
//        try {
//            File f = new File(file);
//            int length = (int) f.length();
//            byte[] data = new byte[length];
//            new FileInputStream(f).read(data);
//            return data;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public static byte[] getFileByteArray(File file) {
//        long fileSize = file.length();
//        if (fileSize > Integer.MAX_VALUE) {
//            System.out.println("file too big...");
//            return null;
//        }
//        byte[] buffer = null;
//        try (FileInputStream fi = new FileInputStream(file)) {
//            buffer = new byte[(int) fileSize];
//            int offset = 0;
//            int numRead = 0;
//            while (offset < buffer.length
//                    && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
//                offset += numRead;
//            }
//            // 确保所有数据均被读取
//            if (offset != buffer.length) {
//                throw new IOException("Could not completely read file "
//                        + file.getName());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return buffer;
//    }
//
//    /**
//     * 功能描述:
//     *
//     * @param inputStream 输入流
//     * @return byte[] 数组
//     * @author xiaobu
//     * @date 2019/3/28 16:03
//     * @version 1.0
//     */
//    public static byte[] inputStream2byte(InputStream inputStream) throws IOException {
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        byte[] buff = new byte[100];
//        int rc = 0;
//        while ((rc = inputStream.read(buff, 0, 100)) > 0) {
//            byteArrayOutputStream.write(buff, 0, rc);
//        }
//        return byteArrayOutputStream.toByteArray();
//    }
//
//    /**
//     * 将file转换为inputStream
//     *
//     * @param file
//     * @return
//     * @throws FileNotFoundException
//     */
//    public static InputStream file2InputStream(File file) throws FileNotFoundException {
//        return new FileInputStream(file);
//    }
//
//}
