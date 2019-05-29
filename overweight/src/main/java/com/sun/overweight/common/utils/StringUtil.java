package com.sun.overweight.common.utils;

import org.springframework.util.CollectionUtils;

import java.util.Collection;

public class StringUtil {

	private StringUtil() {
	}


	public static Boolean isEmpty(String value) {
		return ((value == null)||"".equals(value.trim()));
	}

	public static Boolean isNotEmpty(String value) {
		return !((value == null)||"".equals(value.trim()));
	}

	public static String RandomString(int size) {
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

		StringBuffer sb = new StringBuffer();
		int len = chars.length();
		for (int i = 0; i < size; i++) {
			sb.append(chars.charAt((int) (Math.random() * (len - 1))));
		}
		return sb.toString();
	}

	public static boolean isEmpty(Collection<?> collection) {
		return CollectionUtils.isEmpty(collection);
	}

	public static boolean isNotEmpty(Collection<?> collection) {
		return !isEmpty(collection);
	}

	public static boolean contains(String[] sourceArray,String uri){
		for(int i=0;i<sourceArray.length;i++){
			if(uri.indexOf(sourceArray[i])>-1){
				return true;
			}
		}
		return false;
	}

}

