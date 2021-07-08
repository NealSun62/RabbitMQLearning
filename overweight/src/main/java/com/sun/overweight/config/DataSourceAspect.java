package com.sun.overweight.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;


/**
 * @author sunwx33102
 * @description
 * @date 2021-07-05 10:38
 */
@Aspect
@Component
public class DataSourceAspect {

    @Pointcut("@annotation(com.sun.overweight.config.Ds)")
    public void pointcutConfig(){

    }

    @Before("pointcutConfig()")
    public void before(JoinPoint joinPoint){
        //获得当前访问的class
        Class<?> className = joinPoint.getTarget().getClass();
        //获得访问的方法名
        String methodName = joinPoint.getSignature().getName();
        //得到方法的参数的类型
        Class[] argClass = ((MethodSignature)joinPoint.getSignature()).getParameterTypes();

        String dataSource = null;
        try {
            // 得到访问的方法对象
            Method method = className.getMethod(methodName, argClass);
            // 判断是否存在@DS注解
            if (method.isAnnotationPresent(Ds.class)) {
                Ds annotation = method.getAnnotation(Ds.class);
                // 取出注解中的数据源名
                dataSource = annotation.value();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 切换数据源
        DataSourceContextHolder.setDataSource(dataSource);
    }

    @After("pointcutConfig()")
    public void after(JoinPoint joinPoint){
        DataSourceContextHolder.clearDataSource();
    }
}