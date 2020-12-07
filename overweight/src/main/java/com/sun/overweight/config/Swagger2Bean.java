package com.sun.overweight.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *@Description TODO
 *@Author neal.sun
 *@Date
 */
@Configuration //让Spring来加载该类配置
@EnableSwagger2 //启用Swagger2
public class Swagger2Bean {
    @Value("${swagger.show}")
    private boolean swaggerShow;
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(swaggerShow)
                .groupName("ramp.api.docs")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.sun.overweight"))
                .paths(PathSelectors.any()).build();
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("echo")
                .description("serviceName:echo")
                //.termsOfServiceUrl("http://blog.52itstyle.com")
                //.contact(new Contact("科帮网 ", "http://blog.52itstyle.com", "345849402@qq.com"))
                .version("1.0").build();
    }
}
