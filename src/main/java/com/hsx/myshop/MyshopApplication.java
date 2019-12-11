package com.hsx.myshop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication

public class MyshopApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(MyshopApplication.class, args);
    }

    /*
     *
     * 发布war包到tomcat
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return  builder.sources(MyshopApplication.class);
    }


}
