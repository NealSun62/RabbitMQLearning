package com.sun.overweight.common.utils;
import com.aspose.cells.PdfSaveOptions;
import com.aspose.cells.Workbook;
import com.aspose.slides.Presentation;
import com.aspose.slides.SaveFormat;
import com.aspose.words.Document;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ClassUtils;

import java.io.*;


//@Slf4j
public class OfficeToPdfUtil {

	/**
	 *
	 * @param
	 * @return java.lang.String
	 * @author gezc30201
	 * @date 2020/8/31 11:43
	 */
	public static String getLicensePath() {
		String path = ClassUtils.getDefaultClassLoader().getResource("").getPath().replace("file:", "")
				+ "license.xml";
		System.out.println("path:"+path);
//		path = OfficeToPdfUtil.class.getClass().getClassLoader().getResource("license.xml").getPath();
		File file = new File("overweight/target/classes/license.xml");
		path = file.getAbsolutePath();
		System.out.println("[ license路径 ] path===="+(path));
		return path;
	}

	/**
	 * @return
	 * @description 获取license 去除水印
	 * @author gezc30201
	 * @date 2020/5/9 "\\license.xml" OfficeToPdfUtil.class.getClassLoader()
	 */
	public static boolean getExcelLicense() {
		boolean result = false;
		InputStream is = null;
		try {
			is = new FileInputStream(getLicensePath());
			// InputStream is =
			// ClassUtils.getDefaultClassLoader().getResourceAsStream(getLicensePath());
			com.aspose.cells.License aposeLic = new com.aspose.cells.License();
			aposeLic.setLicense(is);
			result = true;
		} catch (Exception e) {
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
        System.out.println("getExcelLicense:result" + result);
		return result;
	}

	/**
	 * 去除水印
	 *
	 * @param
	 * @return boolean
	 * @author gezc30201
	 * @date 2020/8/12 19:38
	 *
	 */
	public static boolean getWordLicense() {
		boolean result = false;
		InputStream is = null;
		try {
			is = new FileInputStream(getLicensePath());
			// is =
			// ClassUtils.getDefaultClassLoader().getResourceAsStream(getLicensePath());
            System.out.println("is->" + is.available());
			com.aspose.words.License aposeLic = new com.aspose.words.License();
			aposeLic.setLicense(is);
			result = true;
		} catch (Exception e) {
            System.out.println(ExceptionUtils.getTrace(e));
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
        System.out.println("getWordLicense:result" + result);
		return result;
	}

	/**
	 * 去除水印
	 *
	 * @param
	 * @return boolean
	 * @author gezc30201
	 * @date 2020/8/12 19:38
	 */
	public static boolean getPptLicense() {
		boolean result = false;
		InputStream is = null;
		try {
			is = new FileInputStream(getLicensePath());
			// is =
			// ClassUtils.getDefaultClassLoader().getResourceAsStream(getLicensePath());
			System.out.println("is->" + is.available());
			com.aspose.slides.License aposeLic = new com.aspose.slides.License();
			aposeLic.setLicense(is);
			result = true;
		} catch (Exception e) {
			System.out.println(ExceptionUtils.getTrace(e));
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		System.out.println("getPptLicense:result" + result);
		return result;
	}

	/**
	 * @param sourceFilePath excel文件
	 * @param desFilePathd   pad 输出文件目录
	 * @return void
	 * @description excel 转为pdf 输出。
	 * @author gezc30201
	 * @date 2020/5/9
	 */
	public static void excel2pdf(String sourceFilePath, String desFilePathd) {
		// 验证License 若不验证则转化出的pdf文档会有水印产生
		if (!getExcelLicense()) {
			return;
		}
		try {
			// 原始excel路径
			Workbook wb = new Workbook(sourceFilePath);

			FileOutputStream fileOS = new FileOutputStream(desFilePathd);
			PdfSaveOptions pdfSaveOptions = new PdfSaveOptions();
			// 缩放到一个页面（如果列太多 太长）
			pdfSaveOptions.setOnePagePerSheet(true);

			/**
			 * 自动缩放了
			 */
			/*
			 * int[] autoDrawSheets={3}; //当excel中对应的sheet页宽度太大时，在PDF中会拆断并分页。此处等比缩放。
			 * autoDraw(wb,autoDrawSheets);
			 */

			/**
			 * 以下代码打开则只显示第一个sheet
			 */
			/*
			 * int[] showSheets={0}; //隐藏workbook中不需要的sheet页。 printSheetPage(wb,showSheets);
			 */
			wb.save(fileOS, pdfSaveOptions);
			fileOS.flush();
			fileOS.close();

		} catch (Exception e) {
			System.out.println(ExceptionUtils.getTrace(e));
			System.out.println("excel转换pdf失败");
		}
	}

	/**
	 * @param inbytes excel输入流的字节数组
	 * @return byte[]
	 * @description 描述
	 * @author gezc30201
	 * @date 2020/5/9
	 */
	public static byte[] excel2pdf(byte[] inbytes) throws IOException {
		// 验证License 若不验证则转化出的pdf文档会有水印产生
		if (!getExcelLicense()) {
			System.out.println("excelTopdf license不通过");
			return null;
		}
		ByteArrayOutputStream pdfstream = null;
        byte[] outbytes = null;
		try {
			// 原始excel流
			Workbook wb = new Workbook(new ByteArrayInputStream(inbytes));
			// 保存转成pdf的流
			pdfstream = new ByteArrayOutputStream();
			PdfSaveOptions pdfSaveOptions = new PdfSaveOptions();
			// 缩放到一个页面（如果列太多 太长）
			pdfSaveOptions.setOnePagePerSheet(true);

			wb.save(pdfstream, pdfSaveOptions);
			outbytes = pdfstream.toByteArray();
			return outbytes;
		} catch (Exception e) {
			System.out.println(ExceptionUtils.getTrace(e));
		} finally {
			if (pdfstream != null) {
				pdfstream.close();
			}
		}
		return outbytes;
	}

	public static byte[] ppt2pdf(byte[] inbytes) throws IOException {
		// 验证License 若不验证则转化出的pdf文档会有水印产生
		if (!getPptLicense()) {
			System.out.println("pptTopdf license不通过");
			return null;
		}
        byte[] outbytes = null;
		ByteArrayOutputStream pdfstream = null;
		try {
			Presentation ppt = new Presentation(new ByteArrayInputStream(inbytes));
			pdfstream = new ByteArrayOutputStream();
			ppt.save(pdfstream, SaveFormat.Pdf);

			outbytes = pdfstream.toByteArray();
			return outbytes;
		} catch (Exception e) {
			System.out.println(ExceptionUtils.getTrace(e));
		} finally {
			if (pdfstream != null) {
				pdfstream.close();
			}
		}
		return outbytes;
	}

	public static byte[] word2pdf(byte[] inbytes) throws IOException {
		// 验证License 若不验证则转化出的pdf文档会有水印产生
		if (!getWordLicense()) {
			System.out.println("wordTopdf license不通过");
			return null;
		}
		ByteArrayOutputStream pdfstream = null;
        byte[] outbytes = null;
        try {
			// Address是将要被转化的word文档
			Document doc = new Document(new ByteArrayInputStream(inbytes));
			pdfstream = new ByteArrayOutputStream();
			doc.save(pdfstream, com.aspose.words.SaveFormat.PDF);

			outbytes = pdfstream.toByteArray();
			return outbytes;
		} catch (Exception e) {
			System.out.println(ExceptionUtils.getTrace(e));
		} finally {
			if (pdfstream != null) {
				pdfstream.close();
			}
		}
        return outbytes;
    }

	/**
	 * @param wb
	 * @param page 自动拉伸的页的sheet数组
	 * @return void
	 * @description 设置打印的sheet 自动拉伸比例
	 * @author gezc30201
	 * @date 2020/5/9
	 */
	public static void autoDraw(Workbook wb, int[] page) {
		if (null != page && page.length > 0) {
			for (int i = 0; i < page.length; i++) {
				wb.getWorksheets().get(i).getHorizontalPageBreaks().clear();
				wb.getWorksheets().get(i).getVerticalPageBreaks().clear();
			}
		}
	}

	/**
	 * 隐藏workbook中不需要的sheet页。
	 *
	 * @param wb
	 * @param page 显示页的sheet数组
	 */
	public static void printSheetPage(Workbook wb, int[] page) {
		for (int i = 1; i < wb.getWorksheets().getCount(); i++) {
			wb.getWorksheets().get(i).setVisible(false);
		}
		if (null == page || page.length == 0) {
			wb.getWorksheets().get(0).setVisible(true);
		} else {
			for (int i = 0; i < page.length; i++) {
				wb.getWorksheets().get(i).setVisible(true);
			}
		}
	}

}
