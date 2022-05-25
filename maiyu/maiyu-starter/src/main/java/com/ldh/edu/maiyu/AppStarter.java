package com.ldh.edu.maiyu;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableDiscoveryClient
@EnableFeignClients(basePackages = {})
//@EnableEurekaClient
@MapperScan(value = {
        "com.ldh.edu.maiyu.*.datainterface"
})
@EnableCaching
@ServletComponentScan("com.ldh.edu.maiyu")
@EnableTransactionManagement
@SpringBootApplication
@EnableSwagger2
public class AppStarter {
    public static void main(String[] args) {
        SpringApplication.run(AppStarter.class,args);
    }
}
