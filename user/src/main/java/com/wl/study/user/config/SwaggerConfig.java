package com.wl.study.user.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: swagger配置类
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2019/9/21
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        tokenPar.name("header").description("平台").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("wl-study-user")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wl.study.user"))
//                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                // 不显示错误的接口地址
                .paths(Predicates.not(PathSelectors.regex("/error.*")))// 错误路径不监控
                .build()
                .globalOperationParameters(pars)
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "学习平台-用户后台管理 - api",
                "Some custom description of API.",
                "API TOS",
                "Terms of service",
                "myeaddress@company.com",
                "License of API",
                "API license URL");
        return apiInfo;
    }
}


