package com.sun.overweight.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


/**
 * @author sunwx33102
 * @description
 * @date 2021-07-05 10:43
 */

@Configuration
public class DataSourceConfig {

    /**
     *  数据源1
     */
    @Bean(name = "ds1")
    @ConfigurationProperties(prefix = "spring.datasource.druid.ds1")
    public DataSource Ds1(){
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 数据源2
     */
    @Bean(name = "ds2")
    @ConfigurationProperties(prefix = "spring.datasource.druid.ds2")
    public DataSource Ds2(){
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 数据源切换: 通过AOP在不同数据源之间动态切换
     */
    @Primary
    @Bean
    public DataSource dynamicDataSource(){

        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        //设置默认数据源
        dynamicDataSource.setDefaultTargetDataSource(Ds1());
        //配置多数据源
        Map<Object,Object> dsMap = new HashMap<>();
        dsMap.put("ds1",Ds1());
        dsMap.put("ds2",Ds2());

        dynamicDataSource.setTargetDataSources(dsMap);
        return dynamicDataSource;
    }

    /**
     * 配置@Transactional注解事务
     * @return
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource());
    }
}