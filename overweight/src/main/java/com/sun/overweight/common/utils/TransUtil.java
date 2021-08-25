package com.sun.overweight.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
//import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
//import org.apache.commons.beanutils.ConvertUtils;
import com.sun.overweight.common.constant.Constants;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 功能点：类型转换
 *
 * @author: sunbf20942
 * @date: 2020/6/22-16:46
 */
public class TransUtil {
    private static final Pattern LINE_PATTERN = Pattern.compile("_(\\w)");

    /**
     * 驼峰转下划线
     *
     * @param str
     * @return
     */
    public static String camelToUnderline(String str) {
        if (str == null || str.trim().isEmpty()) {
            return "";
        }
        int len = str.length();
        StringBuilder sb = new StringBuilder(len);
        sb.append(str.substring(0, 1).toLowerCase());
        for (int i = 1; i < len; i++) {
            char c = str.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append("_");
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static Map<String, Object> camelToUnderline(Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>(Constants.DEFAULT_SIZE);
        if (map == null || CollectionUtils.isEmpty(map.entrySet())) {
            return result;
        }
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            result.put(camelToUnderline(entry.getKey()), entry.getValue());
        }
        return result;
    }

    /**
     * 下划线转驼峰
     *
     * @param str
     * @return
     */
    public static String underlineToCamel(String str) {
        if (str == null) {
            return null;
        }
        str = str.toLowerCase();
        Matcher matcher = LINE_PATTERN.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 对象 转 UnderLineMap(仅适用简单对象)
     *
     * @param bean
     * @param <T>
     * @return
     */
    public static <T> Map<String, Object> beanToMap(Object bean) {
        return beanToMap(bean, Boolean.TRUE);
    }

    /**
     * 对象 转 UnderLineMap(仅适用简单对象)
     *
     * @param bean
     * @param camelToUnderlineFlag
     * @param <T>
     * @return
     */
    public static <T> Map<String, Object> beanToMap(Object bean, Boolean camelToUnderlineFlag) {
        Map<String, Object> map = Maps.newHashMap();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                if (camelToUnderlineFlag != null && camelToUnderlineFlag) {
                    map.put(camelToUnderline(String.valueOf(key)), beanMap.get(key));
                } else {
                    map.put(String.valueOf(key), beanMap.get(key));
                }
            }
        }
        return map;
    }

    /**
     * UnderLineMap 转 对象(仅适用简单对象)
     *
     * @param map
     * @param <T>
     * @return
     */
//    public static <T, E> T mapToBean(Map<String, Object> map, Class<T> clazz, Class<E> extResultType, String... ignoreProperties) {
//        try {
//            map = camelToUnderline(map);
//            T t = clazz.newInstance();
//            Method[] methods = clazz.getMethods();
//            List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);
//            for (Method m : methods) {
//                if (m.getName().startsWith("set")) {
//                    m.setAccessible(true);
//                    char[] charArray = m.getName().replace("set", "").toCharArray();
//                    charArray[0] += 32;
//                    String field = new String(charArray);
//                    if (ignoreList!=null && ignoreList.contains(field)) {
//                        continue;
//                    }
//                    String str = camelToUnderline(field);
//                    Object o = map.get(str);
//                    if (null != o) {
//                        if (o instanceof JSONObject) {
//                            m.invoke(t, mapToBean((Map<String, Object>) o, m.getParameters()[0].getType(), null));
//                        } else if (o instanceof JSONArray) {
//                            Type genericType = m.getParameters()[0].getParameterizedType();
//                            if ("int[]".equals(genericType.getTypeName())) {
//                                m.invoke(t, jsonArrayToIntArray((JSONArray) o));
//                            } else if (PageInfo.class.equals(clazz)) {
//                                m.invoke(t, listMapToListBean((List<Map>) o, extResultType));
//                            } else {
//                                ParameterizedType pt = (ParameterizedType) genericType;
//                                Class<?> actualTypeArgument = (Class<?>) pt.getActualTypeArguments()[0];
//                                m.invoke(t, listMapToListBean((List<Map>) o, actualTypeArgument));
//                            }
//                        } else {
//                            if (o != null && Date.class.equals(m.getParameters()[0].getType())) {
//                                Field clazzField = clazz.getDeclaredField(field);
//                                clazzField.setAccessible(true);
//                                JsonFormat annotation = clazzField.getAnnotation(JsonFormat.class);
//                                if (annotation != null) {
//                                    String pattern = annotation.pattern();
//                                    SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
//                                    m.invoke(t, dateFormat.parse(o.toString()));
//                                } else if (o instanceof Date) {
//                                    m.invoke(t, o);
//                                } else if (o.toString().length() == 8) {
//                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
//                                    m.invoke(t, dateFormat.parse(o.toString()));
//                                } else if (o.toString().length() == 19) {
//                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                                    m.invoke(t, dateFormat.parse(o.toString()));
//                                }
//                            } else {
//                                m.invoke(t, ConvertUtils.convert(o, m.getParameters()[0].getType()));
//                            }
//                        }
//                    }
//                }
//            }
//            return t;
//        } catch (Exception e) {
//            throw new RuntimeException("数据转换出错！", e);
//        }
//    }

    /**
     * ListUnderLineMap 转 ListBean(仅适用简单对象)
     *
//     * @param list
//     * @param clazz
//     * @param <T>
//     * @return
//     */
//    public static <T> List<T> listMapToListBean(List<Map> list, Class<T> clazz) {
//        if (CollectionUtils.isEmpty(list)) {
//            return Collections.EMPTY_LIST;
//        }
//        List<T> result = new ArrayList<>(list.size());
//        for (Map<String, Object> map : list) {
//            T t = mapToBean(map, clazz, null);
//            result.add(t);
//        }
//        return result;
//    }

    public static int[] jsonArrayToIntArray(JSONArray intArray) {
        if (intArray != null && intArray.size() > 0) {
            int[] result = new int[intArray.size()];
            for (int i = 0; i < intArray.size(); i++) {
                result[i] = (int) intArray.get(i);
            }
            return result;
        } else {
            return null;
        }
    }

}
