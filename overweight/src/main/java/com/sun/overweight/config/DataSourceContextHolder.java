package com.sun.overweight.config;
import lombok.extern.slf4j.Slf4j;

/**
 * @author sunwx33102
 * @description
 * @date 2021-07-05 09:53
 */
@Slf4j
public class DataSourceContextHolder {
//    private static Logger LOG = Logger.getLogger(DataSourceContextHolder.class);

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    /**
     * 设置数据源名称
     */
    public static void setDataSource(String dataSource) {
        log.info("切换到{" + dataSource + "}数据源");
        contextHolder.set(dataSource);
    }

    public static String getDataSource() {
        return contextHolder.get();
    }

    /**
     * 清除数据源
     */
    public static void clearDataSource() {
        contextHolder.remove();
    }

}