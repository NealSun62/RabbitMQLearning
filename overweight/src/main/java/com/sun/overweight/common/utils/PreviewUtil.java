package com.sun.overweight.common.utils;


import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;

/**
 * @author gezc30201
 * @description 描述
 * @date 2020/5/15
 */
public class PreviewUtil {

    private static final Integer DOWNLOAD_LENS = 1 << 10;

    /**
     * description 图片预览
     *
     * @param response
     * @return void
     * @author gezc30201
     * @date 2020/5/15
     */
    public static void imgPreview(HttpServletResponse response, byte[] bytes) {

        response.setContentType("image/jpeg; charset=UTF-8");
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            int lens = bytes.length;
            int flag = 0;
            while (lens > 0) {
                out.write(Arrays.copyOfRange(bytes, flag * DOWNLOAD_LENS, (lens > DOWNLOAD_LENS) ? (flag + 1) * DOWNLOAD_LENS : (flag * DOWNLOAD_LENS + lens)));
                flag++;
                lens -= DOWNLOAD_LENS;
            }
            out.flush();
        } catch (IOException e) {
//            log.error("预览附件报错。附件id:" + downloadAttach.getAttachId().replaceAll("[\r\n]", ""));
//            log.error(ExceptionUtils.getTrace(e));
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
//                    log.error(ExceptionUtils.getTrace(e));
                }
            }
        }
    }



    /**
     * description txt
     *
     * @param response
     * @return void
     * @author gezc30201
     * @date 2020/5/15
     */
    public static void txtPreview(HttpServletResponse response, byte[] bytes) {
        OutputStream out = null;
        try {
            response.setHeader("content-length", String.valueOf(bytes.length));
            // 预览
            response.setContentType("text/plain;charset=utf-8");
            // response.setHeader("content-disposition", "inline;filename=" + java.net.URLEncoder.encode(downloadAttach.getFileName(), "UTF-8"));
            out = response.getOutputStream();
            //os.write(bytes,0,bytes.length);
            int lens = bytes.length;
            int flag = 0;
            while (lens > 0) {
                out.write(Arrays.copyOfRange(bytes, flag * DOWNLOAD_LENS, (lens > DOWNLOAD_LENS) ? (flag + 1) * DOWNLOAD_LENS : (flag * DOWNLOAD_LENS + lens)));
                flag++;
                lens -= DOWNLOAD_LENS;
            }
        } catch (IOException e) {
//            log.error("预览附件报错。附件id:" + downloadAttach.getAttachId().replaceAll("[\r\n]", ""));
//            log.error(ExceptionUtils.getTrace(e));
        } finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
//                    log.error(ExceptionUtils.getTrace(e));
                }
            }
        }
    }


}
