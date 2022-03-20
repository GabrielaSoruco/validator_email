package com.ada.demo.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket apiDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ada.demo.controllers.impl"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo());
    }

    private ApiInfo getApiInfo(){
        return new ApiInfo(
                "Order Service API",
                "Order Service API Description",
                "1.0",
                "http://codmind.com/terms",
                new Contact("Codmind", "https://codmind.com", "apis@codmind.com"),
                "LICENSE",
                "LICENSE URL",
                Collections.emptyList()
        );
    }
}
/*
*
* "SWAGGER DEMO DOCUMENTATION", //Name
                "WWW.EJEMPLOSWAGGER.COM", //Description
                "VERSION 1.0", //Version
                "TERMS OF SERVICE URL", //Terms of service url
                 new Contact("Gabriela Soruco", "HTTPS://WWW.JAVADESDE0.COM", "SORUCO.GABI@GMAIL.COM"),
                "LICENCIA", //License
                "HTTP://WWW.GOOGLE.COM" //Terms of license url */
