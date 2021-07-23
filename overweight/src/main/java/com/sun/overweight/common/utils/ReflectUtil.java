package com.sun.overweight.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射 帮助类
 *
 * @author sunbf20942
 * @date
 */
@Component
@Slf4j
public class ReflectUtil {

   public static String getSetMethodName(String fieldName) {
       if (StringUtils.isBlank(fieldName)) {
           return null;
       }
       return "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
   }

    public static <K> Object getFieldValue(K object, String fieldName, Object nullValue) {
        Object result = nullValue;
        try {
            Method method = object.getClass().getMethod("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1));
            result = method.invoke(object);
            if (result ==null) {
                result = nullValue;
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return result;
    }

    public static <K> Method getMethod(K object, String methoddName, Class<?>... parameterTypes) {
        Method method = null;
        try {
            method = object.getClass().getMethod(methoddName, parameterTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return method;
    }
}
