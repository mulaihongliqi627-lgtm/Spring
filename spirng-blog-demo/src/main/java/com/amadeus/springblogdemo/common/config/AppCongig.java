package com.amadeus.springblogdemo.common.config;


import com.amadeus.springblogdemo.common.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class AppCongig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    List<String> excludePath = List.of(
            "/user/login",
            "/**/*.html",
            "/blog-editormd/**",
            "/css/**",
            "/js/**",
            "/pic/**",
            "favicon.ico"

    );

    //配置拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludePath);
    }

}
