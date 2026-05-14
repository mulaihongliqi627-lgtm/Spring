package com.amadeus.bookdemo.config;

import com.amadeus.bookdemo.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


@Configuration
public class WebConfig  implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    //定义拦截器排除的url路径
    private List<String> excludePaths = List.of(
            "/user/login",
            "/**/*.js",
            "/**/*.css",
            "/**/*.jpg",
            "/**/*.png",
            "/**/*.html");

    //添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")//拦截所有的url请求
                .excludePathPatterns(excludePaths);//排除定义的url请求
    }
}
