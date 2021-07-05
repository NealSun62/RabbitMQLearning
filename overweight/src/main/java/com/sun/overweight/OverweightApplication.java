package com.sun.overweight;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author hspcadmin
 * @date 2021
 */

@MapperScan("com.sun.overweight.mapper")
@EnableTransactionManagement
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class OverweightApplication {

    public static void main(String[] args) {
        SpringApplication.run(OverweightApplication.class, args);
    }

}
