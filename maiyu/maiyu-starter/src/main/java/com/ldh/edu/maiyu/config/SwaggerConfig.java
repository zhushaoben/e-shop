package com.ldh.edu.maiyu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
//                .pathMapping("/edu/maiyu")
                .select()
                .paths(PathSelectors.ant("/api/**"))
                .build()
                .groupName("api");
    }
    @Bean
    public Docket createRestApi2(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
//                .pathMapping("/edu/maiyu")
                .select()
                .paths(PathSelectors.ant("/facade/**"))
                .build()
                .groupName("facade");
    }

    @Bean
    public ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("app-maiyu")
                .description("app-maiyu api document <br/> 服务：商城系统")
                .contact(new Contact("lidahui","https://lidahui.com","lidahui@lidahui.cc"))
                .termsOfServiceUrl("https://eamon.cc")
                .version("1.0.0")
                .build();
    }

}
