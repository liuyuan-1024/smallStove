package com.bug.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * @Author: BugOS-ly
 * @Date: 2022/7/15 21:19
 * @Description: swagger的配置类
 */
@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfig {

    @Bean(value = "defaultApi")
    public Docket defaultApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                //分组名称
                .groupName("smallStove")
                .enable(true)
                .select()
                //方式一: 配置扫描 所有想在swagger界面的统一管理接口。都必须在此包下
                .apis(RequestHandlerSelectors.basePackage("com.bug.api"))
                //方式二: 只有当方法上有  @ApiOperation 注解时才能生成对应的接口文档
                //.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                //所有路径
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("开源小灶API接口文档")
                .description("开源小灶的 RESTFul风格 APIs")
                .termsOfServiceUrl("http://localhost:8080/")
                .contact(new Contact("BugOS-ly", "http://localhost:8080/", "admin@qq.com"))
                .version("1.0")
                .build();
    }
}
