//package com.sun.overweight.common.utils;
//
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.Calendar;
//
///**
// * @author sunwx33102
// * @description
// * @date 2024-12-02 14:21
// */
//public class UploadBusiness {
//
//    /************************************************************
//     * 函数名：uploadFile
//     * 功能描述：处理上传文档，调用格式转换函数将ppt格式转换为pdf，然后将转换后的pdf和原文件存储到指定位置。
//     *输入参数：MultipartFile file     layui上传过来的file
//     *        fileUrl               上传文件的存储地址
//     *返回参数：ResultEntity         上传成功时返回的是重名命的文件名，前端需要读取。
//     *                             上传失败时返回错误信息，前端需要读取
//     *
//     *************************************************************/
//    public static ResultEntity uploadFile(MultipartFile file, String fileUrl) {
//        // 获得文件名
//        String fileName = file.getOriginalFilename();
//        // 获得文件名
//        String oldFileName = fileName.substring(0, fileName.lastIndexOf(BaseConstants.DOT));
//        // 获得文件后缀名
//        String suffix = fileName.substring(fileName.lastIndexOf(BaseConstants.DOT) + 1);
//        // 返回当前calendar对象中时间的毫秒计时
//        long timeInMillis = Calendar.getInstance().getTimeInMillis();
//        //文档重命名，反映到数据表document_url中
////        String newName = oldFileName + timeInMillis + BaseConstants.DOT + suffix;
//
//        //文档重命名，不加原来名字
//        String newName = timeInMillis + BaseConstants.DOT + suffix;
//
//        // 判断文件内容是否为空
//        if (!file.isEmpty()) {
//            try {
//                // filePath=C:\Users\LC\Documents\hzw\resource_path\doc+ \ +新文件名
//                String filePath = fileUrl + File.separator + newName;
//                File pictureUrlFile = new File(fileUrl);
//                // 如果文件存在，则不执行创建文件操作
//                if (!pictureUrlFile.exists()) {
//                    pictureUrlFile.getParentFile().mkdirs();
//                }
//                // File对象，作为 new FileInputStream()的参数要用，作为指向文件的输入流的源。
//                File filePathFile = new File(filePath);
//                // 如果文件不存在，则执行创建文件操作
//                if (!filePathFile.exists()) {
//                    filePathFile.getParentFile().mkdirs();
//                }
//                // 把file文件存储到指定位置
//                file.transferTo(filePathFile);
//
//                // 判断上传文件是否为ppt格式
//                if (suffix.equals('ppt') ){
//
//                    // ppt 转pdf
//                    // 创建输入流
//                    FileInputStream in = new FileInputStream(filePathFile);
//                    // 获得文件名
//                    String prefix = newName.substring(0, newName.lastIndexOf(BaseConstants.DOT));
//                    String newFileName = fileUrl + File.separator + prefix + BaseConstants.DOT + BaseConstants.DOCUMENT_TYPE_PDF;
//
//                    // 调用工具类将ppt转换为pdf
//                    HtmlUtil.ppt2PDF(in, newFileName);
//                    in.close();
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                return ResultEntity.failed("上传失败");
//            }
//        } else {
//            return ResultEntity.failed("请不要上传空文件");
//        }
//        return ResultEntity.successWithData(newName);
//    }
//}