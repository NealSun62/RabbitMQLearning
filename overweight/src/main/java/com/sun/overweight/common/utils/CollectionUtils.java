package com.sun.overweight.common.utils;/*
 * 软件版权: 恒生电子股份有限公司
 * 修改记录:
 * 修改日期     修改人员  修改说明
 * ========    =======  ============================================
 * 2019/6/15 10:46  zhangli25087  新增
 * ========    =======  ============================================
 */


import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 功能说明: 集合工具类
 *
 * @author: zhangli25087 zhangli25087@hundsun.com <br>
 * @date: 2019/6/15 10:46 <br>
 * 功能描述: 写明作用，调用方式，使用场景，以及特殊情况<br>
 */
@Slf4j
public class CollectionUtils {

    /**
     * 方法简介: 判断集合是否为空
     *
     * @param collections :
     * @return
     * @author: zhangli25087 zhangli25087@hundsun.com
     * @date: 2019/6/15 10:48 <br>
     * @describe:
     */
    public static <E> Boolean isBlank(Collection<E> collections) {
        if (collections == null || collections.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 方法简介: 判断集合是否不为空
     *
     * @param collections :
     * @return
     * @author: zhangli25087 zhangli25087@hundsun.com
     * @date: 2019/6/15 10:48 <br>
     * @describe:
     */
    public static <E> Boolean isNotBlank(Collection<E> collections) {
        return !isBlank(collections);
    }

    /**
     * 方法简介: set转list
     *
     * @param sets :
     * @return
     * @author: zhangli25087 zhangli25087@hundsun.com
     * @date: 2019/6/15 10:50 <br>
     * @describe:
     */
    public static <T> List<T> setToList(Set<T> sets) {
        if (isBlank(sets)) {
            return null;
        }
        return new ArrayList<>(sets);
    }

    /**
     * 将list集合中的某一个key转换位list<Object>
     *
     * @param lists
     * @param key
     * @return
     */
    public static <T, K> List<K> cToArr(List<T> lists, String key) {
        if (lists == null) {
            return null;
        }
        List<K> arr = new LinkedList<K>();
        for (int i = 0; i < lists.size(); i++) {
            if (EmeUtils.isNotBlank(getOfKey(lists.get(i), key))) {
                arr.add((K) getOfKey(lists.get(i), key));
            }
        }
        return arr;
    }

    /**
     * 将listBean集合中的某一个key转换位list<Object>
     *
     * @param lists
     * @param key
     * @return
     */
    public static <T, U> List<U> lisBeanToArr(List<T> lists, Function<? super T, ? extends U> keyExtractor) {
        if (lists == null) {
            return null;
        }
        List<U> arr = new ArrayList<U>();
        lists.forEach(l -> {
            arr.add(keyExtractor.apply(l));
        });
        return arr;
    }


    /**
     * 将listBean集合中的某一个key转换位list<Object>
     *
     * @param lists
     * @param key
     * @return
     */
    public static <T, U> List<String> lisBeanToArrKeys(List<T> lists, String splitStr, Function<? super T, ? extends U>... keyExtractor) {
        if (lists == null) {
            return null;
        }
        List<String> arr = new ArrayList<String>();
        lists.forEach(l -> {
            StringBuffer sb = new StringBuffer(EmeUtils.emeToStr(keyExtractor[0].apply(l)));
            for (int i = 1; i < keyExtractor.length; i++) {
                sb.append(splitStr).append(keyExtractor[i].apply(l));
            }
            arr.add(sb.toString());
        });
        return arr;
    }


    /**
     * 将listBean集合中的某一个key转换位list<Object>  去重重复
     *
     * @param lists
     * @param key
     * @return
     */
    public static <T, U> List<U> lisBeanToArrRemoRep(List<T> lists, Function<? super T, ? extends U> keyExtractor) {
        if (lists == null) {
            return null;
        }
        Set<U> arr = new HashSet<>();
        lists.forEach(l -> {
            arr.add(keyExtractor.apply(l));
        });
        return new ArrayList<>(arr);
    }

    /**
     * 方法简介: 获取属性值
     *
     * @param t   :
     * @param key :
     * @return
     * @author: zhangli25087 zhangli25087@hundsun.com
     * @date: 2019/6/18 11:32 <br>
     * @describe:
     */
    private static <T> Object getOfKey(T t, String key) {
        if (t == null) {
            return null;
        }
        if (t instanceof Map) {
            return ((Map) t).get(key);
        } else {
            return getFieldValueByFieldName(key, t);
        }
    }

    /**
     * 将list集合中的某一个key转换位list<Object> 去除重复值
     *
     * @param lists
     * @param key
     * @return
     */
    public static <T> Set<Object> cToArrSet(List<T> lists, String key) {
        if (lists == null) {
            return null;
        }
        Set<Object> arr = new LinkedHashSet<Object>();
        for (int i = 0; i < lists.size(); i++) {
            if (EmeUtils.isNotBlank(getOfKey(lists.get(i), key))) {
                arr.add(getOfKey(lists.get(i), key));
            }
        }
        return arr;
    }

    /**
     * 方法简介: 合并集合
     *
     * @author: zhangli25087 zhangli25087@hundsun.com
     * @date: 2018-11-08 14:47 <br>
     * 功能描述: 写明作用<br>
     * 使用保证T是相同类型
     */
    public static <T> List<T> mergeC(@SuppressWarnings("unchecked") List<T>... lists) {
        if (lists == null) {
            return null;
        }
        List<T> list = lists[0] == null ? new ArrayList<>() : lists[0];
        for (int i = 1; i < lists.length; i++) {
            if (lists[i] == null || lists[i].size() == 0) {
                continue;
            }
            list.addAll(lists[i]);
        }
        return list;
    }

    /**
     * 按照基准数据返回相同长度的数组
     *
     * @param lists      集合
     * @param bencs      基准数据数组
     * @param compareKey 基准数据对比的key
     * @param getKey     需要获取的数据
     * @return
     */
    public static <T, V> List fillArray(List<T> lists, V[] bencs, String compareKey, String getKey) {
        if (bencs == null) {
            return null;
        }
        List<V> arrs = new ArrayList<>(bencs.length);
        if (lists == null) {
            for (int i = 0; i < bencs.length; i++) {
                arrs.add(null);
            }
            return arrs;
        }
        for (int i = 0; i < bencs.length; i++) {
            T t = CollectionUtils.findValuesMap(lists, compareKey, EmeUtils.emeToStr(bencs[i]));
            if (t == null) {
                arrs.add(null);
            } else {
                if (t instanceof Map) {
                    arrs.add((V) ((Map) t).get(getKey));
                } else {
                    arrs.add((V) getFieldValueByFieldName(getKey, t));
                }
            }
        }
        return arrs;
    }

    /**
     * 判断Map集合中某个key的value值是否等于定义的value值
     *
     * @param map
     * @param keyName key的name
     * @param value   定义的value值
     * @return
     */
    public static <T> boolean findValue(T t, String keyName, String value) {
        if (value == null || keyName == null || t == null) {
            return false;
        }
        if (t instanceof Map) {
            return EmeUtils.equals(((Map) t).get(keyName), value);
        } else {
            return EmeUtils.equals(getFieldValueByFieldName(keyName, t), value);
        }
    }

    /**
     * 判断List<Map>集合中某个map中是否有key位keyname时，value值等于传入的值
     *
     * @param map
     * @param keyName key的name
     * @param value   定义的value值
     * @return
     */
    public static <T> T findValuesMap(List<T> lists, String keyName, String value) {
        if (value == null || keyName == null || lists == null) {
            return null;
        }
        for (int i = 0; i < lists.size(); i++) {
            if (findValue(lists.get(i), keyName, value)) {
                return lists.get(i);
            }
        }
        return null;
    }

    /**
     * 查找lists 中 map 的key 的value值是否有传入v，有就返回 lists的index 没有返回-1
     *
     * @return
     */
    public static <K, V> Integer findIndexMap(List<Map<K, V>> lists, V v, String vKey) {
        if (lists == null) {
            return -1;
        }
        for (int i = 0; i < lists.size(); i++) {
            if (v == null) {
                if (lists.get(i).get(vKey) == null) {
                    return i;
                }
                continue;
            }
            if (v.equals(lists.get(i).get(vKey))) {
                return i;
            }
        }
        return -1;
    }


    /**
     * 查找lists 中 map 的key 的value值是否有传入v，有就返回 lists的index 没有返回-1
     *
     * @return
     */
    public static <T> Integer findIndex(List<T> lists, Object v, String vKey) {
        if (lists == null) {
            return -1;
        }
        for (int i = 0; i < lists.size(); i++) {
            if (v == null) {
                if (getFieldValueByFieldName(vKey, lists.get(i)) == null) {
                    return i;
                }
                continue;
            }
            if (EmeUtils.equals(getFieldValueByFieldName(vKey, lists.get(i)), v)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 根据属性名获取属性值
     *
     * @param fieldName
     * @param object
     * @return
     */
    private static Object getFieldValueByFieldName(String fieldName, Object object) {
        try {
            Field field = getFieldByClasss(fieldName, object);
            field.setAccessible(true);
            // 设置对象的访问权限，保证对private的属性的访问
            return field.get(object);
        } catch (Exception e) {
            log.error("fieldName【" + fieldName + "】，" + "Object【" + object + "】;" + e.getMessage());
            log.error(ExceptionUtils.getTrace(e));
            return null;
        }
    }

    /**
     * 根据属性名获取属性元素，包括各种安全范围和所有父类
     *
     * @param fieldName
     * @param object
     * @return
     */
    private static Field getFieldByClasss(String fieldName, Object object) {
        Field field = null;
        Class<?> clazz = object.getClass();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
            } catch (Exception e) {
                log.error(e.getMessage(),e);
                // 这里甚么都不能抛出去。
                // 如果这里的异常打印或者往外抛，则就不会进入
            }
        }
        return field;
    }

    /**
     * 方法简介:  集合返回逗号隔开的字符串
     *
     * @param lists :
     * @param split :
     * @return
     * @author: zhangli25087 zhangli25087@hundsun.com
     * @date: 2019/6/19 12:36 <br>
     * @describe:
     */
    public static <T> String listToStrSplit(List<T> lists, String split) {
        if (isBlank(lists)) {
            return null;
        }
        String str = "";
        for (int i = 0; i < lists.size(); i++) {
            str += EmeUtils.emeToStr(lists.get(i)) + split;
        }
        return str.substring(0, str.length());
    }

    /**
     * 方法简介: List<T>    ->    map<field,T s>      将List<T> 转换成某一个fiel的Map
     *
     * @param lists :
     * @param key   :
     * @return
     * @author: zhangli25087 zhangli25087@hundsun.com
     * @date: 2019/6/27 19:24 <br>
     * @describe:
     */
    public static <K, T> Map<K, T> listToKeyMap(List<T> lists, String fieldName) {
        Map<K, T> mapC = new HashMap<>(16);
        if (isNotBlank(lists)) {
            if (lists.get(0) instanceof Map) {
                lists.forEach(l -> {
                    Map m = (Map) l;
                    mapC.put((K) m.get(fieldName), l);
                });
            } else {
                lists.forEach(l -> {
                    mapC.put((K) getFieldValueByFieldName(fieldName, l), l);
                });
            }
        }
        return mapC;
    }

    /**
     * 方法简介: List<T>    ->    map<field,T s>      将List<T> 转换成某一个fiel的Map
     *
     * @param lists :
     * @param key   :
     * @return
     * @author: zhangli25087 zhangli25087@hundsun.com
     * @date: 2019/6/27 19:24 <br>
     * @describe:
     */
    public static <K, V> Map<V, Map> listToKeyMap2(List<Map<K, V>> lists, K k) {
        Map<V, Map> mapC = new HashMap<>(16);
        if (isNotBlank(lists)) {
            lists.forEach(l -> {
                Map m = (Map) l;
                mapC.put((V) m.get(k), l);
            });
        }
        return mapC;
    }

    /**
     * 方法简介: List<T>    ->    map<field,T s>      将List<T> 转换成某一个fiel的Map
     *
     * @param lists        :
     * @param keyExtractor : 函数
     * @author: zhangli25087 zhangli25087@hundsun.com
     * @date: 2019/6/27 19:24 <br>
     * @describe:
     * @retun
     */
    public static <T, U> Map<U, T> listBeanToKey(List<T> lists, Function<? super T, ? extends U> keyExtractor) {
        Map<U, T> mapC = new HashMap<>(16);
        if (isNotBlank(lists)) {
            lists.forEach(l -> {
                mapC.put(keyExtractor.apply(l), l);
            });
        }
        return mapC;
    }

    /**
     * 方法简介: List<T>    ->    map<field,T s>      将List<T> 转换成多个files的Map
     *
     * @param lists         :
     * @param keyExtractors : 函数集合
     * @author: zhangli25087 zhangli25087@hundsun.com
     * @date: 2019/6/27 19:24 <br>
     * @describe:
     * @retun
     */
    public static <T> Map<String, T> listBeanToKeys(List<T> lists, String splitStr, Function<? super T, ? extends String>... keyExtractors) {
        Map<String, T> mapC = new HashMap<>(16);
        if (isNotBlank(lists) && keyExtractors != null && keyExtractors.length != 0) {
            lists.forEach(l -> {
                String key = "";
                for (int i = 0; i < keyExtractors.length; i++) {
                    key = key + keyExtractors[i].apply(l) + splitStr;
                }
                mapC.put(key.substring(0, key.length() - 1), l);
            });
        }
        return mapC;
    }

    /**
     * 方法简介: listBean 中某一个字段转换成集合
     *
     * @param lists        :
     * @param keyExtractor :
     * @return
     * @author: zhangli25087 zhangli25087@hundsun.com
     * @date: 2019/8/14 21:25 <br>
     * @describe:
     */
    public static <T, U> List<U> listBeanToKeyArr(List<T> lists, Function<? super T, ? extends U> keyExtractor) {
        Set<U> set = new HashSet<>();
        if (isNotBlank(lists)) {
            lists.forEach(l -> {
                if (keyExtractor.apply(l) != null) {
                    set.add(keyExtractor.apply(l));
                }
            });
        }
        return new ArrayList<>(set);
    }

    /**
     * 方法简介: 多个List<V> 保留交集
     *
     * @param lists :
     * @return
     * @author: zhangli25087 zhangli25087@hundsun.com
     * @date: 2019/8/8 20:34 <br>
     * @describe:
     */
    public static <V> List<V> listVExtract(List<V>... lists) {
        Set set = new HashSet<V>();
        if (lists == null || lists.length == 0) {
            return null;
        }
        List<List<V>> arrys = new ArrayList<>();
        // 只要有一个传入空就是返回空集合
        for (int i = 0; i < lists.length; i++) {
            if (isBlank(lists[i])) {
                return null;
            }
            arrys.add(lists[i]);
        }
        if (arrys.size() == 0) {
            return null;
        }
        if (arrys.size() == 1) {
            arrys.get(0).forEach(l -> {
                set.add(l);
            });
            return new ArrayList<>(set);
        }
        List<V> intersection = new ArrayList<>(lists[0]);
        for (int i = 1; i < arrys.size(); i++) {
            if (isNotBlank(arrys.get(i))) {
                intersection.retainAll(arrys.get(i));
            }
        }
        intersection.forEach(l -> {
            set.add(l);
        });
        return new ArrayList<>(set);
    }


    /**
     * 方法简介:  判断两个集合是否有交集
     *
     * @param lists1 :
     * @param lists2 :
     * @return
     * @author: zhangli25087 zhangli25087@hundsun.com
     * @date: 2019/11/30 11:20 <br>
     * @describe:
     */
    public static <V> Boolean checkListExtract(List<V> lists1, List<V> lists2) {
        if (isBlank(lists1) || isBlank(lists2)) {
            return false;
        }
        Integer sizeIn = 1000;
        if ((lists1.size() + lists2.size() > sizeIn)) {
            // 长度超过1000使用map
            Map<V, Object> map1 = new HashMap<>(lists1.size());
            lists1.forEach(li1 -> {
                map1.put(li1, true);
            });
            for (int i = 0; i < lists2.size(); i++) {
                if (map1.get(lists2.get(i)) != null) {
                    return true;
                }
            }
        } else {
            for (int i = 0; i < lists1.size(); i++) {
                if (lists1.get(i) == null) {
                    continue;
                }
                for (int j = 0; j < lists2.size(); j++) {
                    if (lists2.get(j) == null) {
                        continue;
                    }
                    if (lists1.get(i).equals(lists2.get(j))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * 方法简介: 求集合对象A和集合对象B的某一个字段的差集合
     *
     * @param listA        : 集合A对象
     * @param listB        : 集合B对象
     * @param keyExtractor :  属性字段表达式
     * @return
     * @author: zhangli25087 zhangli25087@hundsun.com
     * @date: 2019/8/24 11:05 <br>
     * @describe:
     */
    public static <T, U> List<U> listBeanDifferenceSet(List<T> listA, List<T> listB, Function<? super T, ? extends U> keyExtractor) {
        if (isBlank(listA) || isBlank(listB)) {
            return listBeanToKeyArr(listA, keyExtractor);
        }
        return listEmeDifferenceSet(listBeanToKeyArr(listA, keyExtractor), listBeanToKeyArr(listB, keyExtractor));
    }

    /**
     * 方法简介: 求集合A和集合B的差集合
     *
     * @param listA : 集合A对象
     * @param listB : 集合B对象
     * @return
     * @author: zhangli25087 zhangli25087@hundsun.com
     * @date: 2019/8/24 11:05 <br>
     * @describe:
     */
    public static <V> List<V> listEmeDifferenceSet(List<V> listAEme, List<V> listBEme) {
        if (isBlank(listAEme) || isBlank(listBEme)) {
            return listAEme;
        }
        List<V> sSet = listAEme.stream().filter(a -> !listBEme.contains(a)).collect(Collectors.toList());
        return sSet;
    }


    /**
     * 方法简介: 保留并集
     *
     * @param lists :
     * @return
     * @author: zhangli25087 zhangli25087@hundsun.com
     * @date: 2019/9/29 20:08 <br>
     * @describe:
     */
    public static <V> List<V> listsSum(List<V>... lists) {
        if (lists == null || lists.length <= 0) {
            return null;
        }
        Set<V> set = new HashSet<>();
        for (int i = 0; i < lists.length; i++) {
            if (CollectionUtils.isNotBlank(lists[i])){
                lists[i].forEach(l -> {
                    set.add(l);
                });
            }
        }
        return new ArrayList<>(set);
    }

    /**
     * 方法简介: 多个值组合成list<V>
     *
     * @param vs :
     * @return
     * @author: zhangli25087 zhangli25087@hundsun.com
     * @date: 2019/8/9 14:21 <br>
     * @describe:
     */
    public static <V> List<V> valsToList(V... vs) {
        if (vs == null || vs.length <= 0) {
            return new ArrayList<>();
        }
        List<V> lists = new ArrayList<>();
        for (int i = 0; i < vs.length; i++) {
            if (EmeUtils.isNotBlank(vs[i])) {
                lists.add(vs[i]);
            }
        }
        return lists;
    }

    /**
     * 方法简介: 按字段聚合相同数据到list<Bean>   用Map 得key存储 字段数据 ,  value 是相同key得集合
     *
     * @param lists        :
     * @param keyExtractor :
     * @return
     * @author: zhangli25087 zhangli25087@hundsun.com
     * @date: 2019/8/17 15:59 <br>
     * @describe:
     */
    public static <T, U> Map<U, List<T>> listToKeyMapArr(List<T> lists, Function<? super T, ? extends U> e) {
        if (isBlank(lists)) {
            return new HashMap<U, List<T>>();
        }
        Map<U, List<T>> map = new HashMap<U, List<T>>();
        lists.forEach(l -> {
            if (map.containsKey(e.apply(l))) {
                map.get(e.apply(l)).add(l);
            } else {
                map.put(e.apply(l), new ArrayList<>());
                map.get(e.apply(l)).add(l);
            }
        });
        return map;
    }

    ;

    /**
     * 方法简介: 按字段聚合相同数据到list<Bean>   用Map 得多个key(e)按分隔符(splitStr)存储 字段数据 ,  value 是相同得多个key(e)按分隔符(splitStr)得集合
     *
     * @param lists        :
     * @param keyExtractor :
     * @return
     * @author: zhangli25087 zhangli25087@hundsun.com
     * @date: 2019/8/17 15:59 <br>
     * @describe:
     */
    public static <T, U> Map<String, List<T>> listToKeysMapArr(List<T> lists, String splitStr, Function<? super T, ? extends U>... e) {
        if (isBlank(lists) || e == null || e.length == 0) {
            return new HashMap<String, List<T>>();
        }
        Map<String, List<T>> map = new LinkedHashMap<String, List<T>>();
        lists.forEach(l -> {
            StringBuffer key = new StringBuffer(EmeUtils.emeToStr(e[0].apply(l)));
            for (int i = 1; i < e.length; i++) {
                key.append(splitStr).append(EmeUtils.emeToStr(e[i].apply(l)));
            }
            String keyStr = key.toString();
            if (map.containsKey(keyStr)) {
                map.get(keyStr).add(l);
            } else {
                map.put(keyStr, new ArrayList<>());
                map.get(keyStr).add(l);
            }
        });
        return map;
    }

    ;

    /**
     * 方法简介: 按字段聚合相同数据到list<Map>   用Map 得key存储 字段数据 ,  value 是相同key得集合
     *
     * @param lists        :
     * @param keyExtractor :
     * @return
     * @author: zhangli25087 zhangli25087@hundsun.com
     * @date: 2019/8/17 15:59 <br>
     * @describe:
     */
    public static <K, V> Map<V, List<Map<K, V>>> listToKeyMapArr(List<Map<K, V>> lists, K k) {
        if (isBlank(lists)) {
            return new HashMap<V, List<Map<K, V>>>();
        }
        Map<V, List<Map<K, V>>> map = new HashMap<>(16);
        lists.forEach(l -> {
            if (map.containsKey(l.get(k))) {
                map.get(l.get(k)).add(l);
            } else {
                map.put(l.get(k), new ArrayList<>());
                map.get(l.get(k)).add(l);
            }
        });
        return map;
    }

    ;


    /**
     * 方法简介:  把某一个字段按照分隔符分割连接
     *
     * @param lists        :   数据
     * @param keyExtractor :  字段
     * @param splitStr     :  分隔符
     * @return
     * @author: zhangli25087 zhangli25087@hundsun.com
     * @date: 2019/9/2 16:47 <br>
     * @describe:
     */
    public static <T, U> String listBeanToKeySplitStr(List<T> lists, Function<? super T, ? extends U> keyExtractor, String splitStr) {
        if (CollectionUtils.isBlank(lists) || keyExtractor == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        lists.forEach(l -> {
            sb.append(keyExtractor.apply(l) + splitStr);
        });
        return sb.toString().substring(0, sb.length() - 1);
    }

    /**
     * 方法简介:  比中两个不同bean的集合，提供比中的消费者和生产者  BiConsumer
     *
     * @param listProducers              : 集合生产者
     * @param listConsumers              : 集合消费者
     * @param matchKeyExtractorProducers : 比中的生产者key
     * @param matchKeyExtractorConsumers : 比中的消费者key
     * @param biConsumer                 : 消费比中的生产者和消费者
     * @return
     * @author: zhangli25087 zhangli25087@hundsun.com
     * @date: 2020/3/14 13:48 <br>
     * @describe:
     */
    public static <TP, TC, U> void matchTwoListNotWithSameBean(List<TP> listProducers, List<TC> listConsumers,
                                                               Function<? super TP, ? extends U> matchKeyExtractorProducers,
                                                               Function<? super TC, ? extends U> matchKeyExtractorConsumers,
                                                               BiConsumer<TP, TC> biConsumer) {
        Objects.requireNonNull(listProducers);
        Objects.requireNonNull(listConsumers);
        Objects.requireNonNull(matchKeyExtractorProducers);
        Objects.requireNonNull(matchKeyExtractorConsumers);
        Map<U, TP> mapProducers = listBeanToKey(listProducers, matchKeyExtractorProducers);
        listConsumers.forEach(consumers -> {
            if (mapProducers.get(matchKeyExtractorConsumers.apply(consumers)) != null) {
                biConsumer.accept(mapProducers.get(matchKeyExtractorConsumers.apply(consumers)), consumers);
            }
        });
    }

    /**
     * 方法简介:  比中两个不同bean的集合，返回比中的消费者和生产者Map集合 Map中key是生产者  value是消费者
     *
     * @param listProducers              : 集合生产者
     * @param listConsumers              : 集合消费者
     * @param matchKeyExtractorProducers : 比中的生产者key
     * @param matchKeyExtractorConsumers : 比中的消费者key
     * @return
     * @author: zhangli25087 zhangli25087@hundsun.com
     * @date: 2020/3/14 13:48 <br>
     * @describe:
     */
    public static <TP, TC, U> Map<U, TP> matchTwoListNotWithSameBean(List<TP> listProducers, List<TC> listConsumers,
                                                                     Function<? super TP, ? extends U> matchKeyExtractorProducers,
                                                                     Function<? super TC, ? extends U> matchKeyExtractorConsumers) {
        Objects.requireNonNull(listProducers);
        Objects.requireNonNull(listConsumers);
        Objects.requireNonNull(matchKeyExtractorProducers);
        Objects.requireNonNull(matchKeyExtractorConsumers);
        Map<U, TP> mapProducers = listBeanToKey(listProducers, matchKeyExtractorProducers);
        Map<TP, TC> mapMatcj = new HashMap<>(listConsumers.size());
        listConsumers.forEach(consumers -> {
            if (mapProducers.get(matchKeyExtractorConsumers.apply(consumers)) != null) {
                mapMatcj.put(mapProducers.get(matchKeyExtractorConsumers.apply(consumers)), consumers);
            }
        });
        return mapProducers;
    }


    /**
     * 方法简介:  比中两个相同bean的集合，提供比中的消费者和生产者 BiConsumer
     *
     * @param listProducers              : 集合生产者
     * @param listConsumers              : 集合消费者
     * @param matchKeyExtractorProducers : 比中的生产者key
     * @param matchKeyExtractorConsumers : 比中的消费者key
     * @param biConsumer                 : 消费比中的生产者和消费者 -
     * @return
     * @author: zhangli25087 zhangli25087@hundsun.com
     * @date: 2020/3/14 13:48 <br>
     * @describe:
     */
    public static <T, U> void matchTwoListWithSameBean(List<T> listProducers, List<T> listConsumers,
                                                       Function<? super T, ? extends U> matchKeyExtractorProducers,
                                                       BiConsumer<T, T> biConsumer) {
        Objects.requireNonNull(listProducers);
        Objects.requireNonNull(listConsumers);
        Objects.requireNonNull(matchKeyExtractorProducers);
        Map<U, T> mapProducers = listBeanToKey(listProducers, matchKeyExtractorProducers);
        listConsumers.forEach(consumers -> {
            if (mapProducers.get(matchKeyExtractorProducers.apply(consumers)) != null) {
                biConsumer.accept(mapProducers.get(matchKeyExtractorProducers.apply(consumers)), consumers);
            }
        });
    }


    /**
     * 方法简介:  比中两个相同bean的集合，返回比中的消费者和生产者Map集合 Map中key是生产者  value是消费者
     *
     * @param listProducers              : 集合生产者
     * @param listConsumers              : 集合消费者
     * @param matchKeyExtractorProducers : 比中的生产者key
     * @param matchKeyExtractorConsumers : 比中的消费者key
     * @return
     * @author: zhangli25087 zhangli25087@hundsun.com
     * @date: 2020/3/14 13:48 <br>
     * @describe:
     */
    public static <T, U> Map<T, T> matchTwoListWithSameBean(List<T> listProducers, List<T> listConsumers,
                                                            Function<? super T, ? extends U> matchKeyExtractorProducers) {
        Objects.requireNonNull(listProducers);
        Objects.requireNonNull(listConsumers);
        Objects.requireNonNull(matchKeyExtractorProducers);
        Map<U, T> mapProducers = listBeanToKey(listProducers, matchKeyExtractorProducers);
        Map<T, T> mapMatcj = new HashMap<>(listConsumers.size());
        listConsumers.forEach(consumers -> {
            if (mapProducers.get(matchKeyExtractorProducers.apply(consumers)) != null) {
                mapMatcj.put(mapProducers.get(matchKeyExtractorProducers.apply(consumers)), consumers);
            }
        });
        return mapMatcj;
    }


}
