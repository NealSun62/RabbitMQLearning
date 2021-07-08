/*
 * 软件版权: 恒生电子股份有限公司
 * 修改记录:
 * 修改日期     修改人员  修改说明
 * ========    =======  ============================================
 * 2019-03-29 10:09:50  zhangli25087  新增
 * ========    =======  ============================================
 */
package com.sun.overweight.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 功能说明: 异常工具类
 *
 * @author: zhangli25087 zhangli25087@hundsun.com <br>
 * @date: 2019-03-29 10:09:50 <br>
 * 功能描述: 写明作用，调用方式，使用场景，以及特殊情况<br>
 */
public class ExceptionUtils {

    /**
     * 方法简介: 获取异常的字符串
     *
     * @author: zhangli25087 zhangli25087@hundsun.com
     * @date: 2019-03-12 15:26 <br>
     * 功能描述: 写明作用<br>
     */
    public static String getTrace(Throwable t) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        t.printStackTrace(writer);
        StringBuffer buffer = stringWriter.getBuffer();
        return buffer.toString();
    }
}


