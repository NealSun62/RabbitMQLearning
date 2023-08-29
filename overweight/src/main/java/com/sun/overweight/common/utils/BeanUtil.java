//package com.sun.overweight.common.utils;
//
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.BeanWrapper;
//import org.springframework.beans.BeanWrapperImpl;
//import org.springframework.beans.PropertyAccessorFactory;
//
//import java.beans.PropertyDescriptor;
//import java.util.*;
//
///**
// * @description: javabean工具类
// * @author: chenyk25600
// * @date: 2019/6/14
// */
//@Slf4j
//public class BeanUtil {
//
//    /**
//     * 拷贝
//     *
//     * @param source
//     * @param target
//     * @param ignoreProperties
//     * @return
//     */
//    public static Object copyProperties(Object source, Object target, String... ignoreProperties) {
//        if (source == null) {
//            return target;
//        }
//        BeanUtils.copyProperties(source, target, ignoreProperties);
//        return target;
//    }
//
//    /**
//     * 拷贝
//     *
//     * @param sources
//     * @param clazz
//     * @param <T>
//     * @return
//     */
//    public static <T, V> List<T> copyList(List<V> sources, Class<T> clazz) {
//        return copyList(sources, clazz, null);
//    }
//
//    /**
//     * 拷贝
//     *
//     * @param sources
//     * @param clazz
//     * @param callback
//     * @param <T>
//     * @return
//     */
//    public static <T, V> List<T> copyList(List<V> sources, Class<T> clazz, Callback<T, V> callback) {
//        List<T> targetList = new ArrayList<>();
//        if (sources != null) {
//            try {
//                for (V source : sources) {
//                    T target = clazz.newInstance();
//                    copyProperties(source, target);
//                    if (callback != null) {
//                        callback.set(source, target);
//                    }
//                    targetList.add(target);
//                }
//            } catch (InstantiationException e) {
//                log.error(e.getMessage(),e);
//            } catch (IllegalAccessException e) {
//            	log.error(e.getMessage(),e);
//            }
//        }
//        return targetList;
//    }
//
//    /**
//     * toMap
//     *
//     * @param bean
//     * @param ignoreProperties
//     * @return
//     */
//    public static Map<String, Object> toMap(Object bean, String... ignoreProperties) {
//        Map<String, Object> map = new LinkedHashMap<>();
//        List<String> ignoreList = new ArrayList<>(Arrays.asList(ignoreProperties));
//        ignoreList.add("class");
//        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(bean);
//        for (PropertyDescriptor pd : beanWrapper.getPropertyDescriptors()) {
//            if (!ignoreList.contains(pd.getName()) && beanWrapper.isReadableProperty(pd.getName())) {
//                Object propertyValue = beanWrapper.getPropertyValue(pd.getName());
//                map.put(pd.getName(), propertyValue);
//            }
//        }
//        return map;
//    }
//
//    /**
//     * toBean
//     *
//     * @param map
//     * @param beanType
//     * @param <T>
//     * @return
//     */
//    public static <T> T toBean(Map<String, Object> map, Class<T> beanType) {
//        BeanWrapper beanWrapper = new BeanWrapperImpl(beanType);
//        map.forEach((key, value) -> {
//            if (beanWrapper.isWritableProperty(key)) {
//                beanWrapper.setPropertyValue(key, value);
//            }
//        });
//        return (T) beanWrapper.getWrappedInstance();
//    }
//
//    public interface Callback<T, V> {
//        /**
//         * 设值
//         *
//         * @param source
//         * @param target
//         */
//        void set(V source, T target);
//    }
//
//}
