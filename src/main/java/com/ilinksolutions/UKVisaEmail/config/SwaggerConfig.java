package com.ilinksolutions.UKVisaEmail.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

public class SwaggerConfig {
	@Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ilinksolutions.UKVisaEmail.rservices"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaInfo());
    }

    private ApiInfo metaInfo() {

        ApiInfo apiInfo = new ApiInfo(
                "UK Visa Email API",
                "This service is used for testing the UK Visa Email Rest APIs",
                "1.0",
                "Terms of Service",
                new Contact("I-Link Solutions, Inc.", "http://ilinksolution.com/",
                        "junaid.qureshi@ilinksolution.com"),
                "I-Link Solutions, Inc. License Version 2.0",
                "http://ilinksolution.com/", Collections.emptyList()
        );

        return apiInfo;
    }
	

}
