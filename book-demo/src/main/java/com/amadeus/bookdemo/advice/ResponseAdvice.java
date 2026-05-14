package com.amadeus.bookdemo.advice;

import com.amadeus.bookdemo.model.Result;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@RestControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {


    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // true 表示执行 beforeBodyWrite 方法
        return true;
    }

    @Override
    public @Nullable Object beforeBodyWrite(@Nullable Object body,
                                            MethodParameter returnType,
                                            MediaType selectedContentType,
                                            Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                            ServerHttpRequest request,
                                            ServerHttpResponse response) {
        // body: 目标方法返回的结果
        
        // 如果已经是 Result 类型，直接返回
        if (body instanceof Result) {
            return body;
        }
        
        // 如果返回类型是 String，需要特殊处理（避免双重序列化）
        if(body instanceof String){
            Result result = Result.success(body);
            return objectMapper.writeValueAsString(result);
        }
        
        // 其他类型统一包装为 Result
        return Result.success(body);
    }
}
