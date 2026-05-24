package com.amadeus.springblogdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.amadeus.springblogdemo.mapper")
@SpringBootApplication
public class SpringBlogDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBlogDemoApplication.class, args);
    }
}
