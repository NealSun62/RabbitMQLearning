package com.sun.overweight.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * dom4j工具类
 * @author ouyangjun
 */
public class Dom4jUtils {

	/**
	 * dom4j xml生成方法
	 * @param file
	 * @param list
	 * @param clz
	 */
	@SuppressWarnings("unchecked")
	public static <T> void createXml(File file, List<T> list, Class<T> clz){
		try{
			// 创建Document
			Document document = DocumentHelper.createDocument();
			// 创建根节点
			Element root = document.addElement("root");
			// 获取类中所有的字段
			Field[] fields = clz.getDeclaredFields();
			// 先把List<T>对象转成json字符串
			String str = JSONObject.toJSONString(list);
			// 把json字符串转换成List<Map<Object, Object>>
			List<Map<Object, Object>> mapList = (List<Map<Object, Object>>)JSONArray.parse(str);

			Element element;
			Map<Object,Object> map;
			// 迭代拼接xml节点数据
			for (int i=0; i<mapList.size(); i++) {
				// 在根节点下添加子节点
				element = root.addElement(clz.getSimpleName());
				// 获取Map<Object, Object>对象
				map = mapList.get(i);
				// 从map中获取数据，拼接xml
				for(Field field : fields){
					// 在子节点下再添加子节点
					element.addElement(field.getName())
							.addAttribute("attr", field.getType().getName())
							.addText(String.valueOf(map.get(field.getName())));
				}
			}

			// 把xml内容输出到文件中
			OutputFormat format = OutputFormat.createPrettyPrint();
			XMLWriter writer = new XMLWriter( new FileOutputStream(file), format);
			writer.write(document);
			System.out.println("Dom4jUtils Create Xml success!");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
