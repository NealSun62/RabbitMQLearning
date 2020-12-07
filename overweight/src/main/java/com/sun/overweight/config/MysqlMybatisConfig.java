//package com.sun.overweight.config;
//
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.core.io.support.ResourcePatternResolver;
//
//import javax.sql.DataSource;
//
//
//@Configuration
//@MapperScan(basePackages = {"com.znjt.automatic.ramp.mysqlmapper"}, sqlSessionTemplateRef = "mysqlSqlSessionTemplate")
//public class MysqlMybatisConfig {
//    @Bean(name = "mysqlDataSource")
//
//    /** prefix值必须是application.properteis中对应属性的前缀 */
//    @ConfigurationProperties(prefix = "mysql.spring.datasource")
//    public DataSource mysqlDataSource() {
//        return DataSourceBuilder.create().build();
//    }
//    @Bean
//    public SqlSessionTemplate mysqlSqlSessionTemplate(@Qualifier("mysqlSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception
//    {
//        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory);
//        return template;
//    }
//
//
//    @Bean
//    public SqlSessionFactory mysqlSqlSessionFactory(@Qualifier("mysqlDataSource") DataSource dataSource) throws Exception {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dataSource);
//        //添加XML目录
//        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        try {
//            bean.setMapperLocations(resolver.getResources("classpath:mybatis/mysqlmapper/*.xml"));
//            return bean.getObject();
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }
//
//
//}
//
