package com.amadeus.springblogdemo.common.advice;


import com.amadeus.springblogdemo.common.exception.BlogException;
import com.amadeus.springblogdemo.pojo.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {
    
    @ExceptionHandler(BlogException.class)
    public Result handle(BlogException e){
        log.error("BlogException: ", e);
        return Result.fail(e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoResourceFoundException.class)
    public Result handle(NoResourceFoundException e){
        log.warn("No static resource: {}", e.getResourcePath());
        return Result.fail("资源不存在");
    }
    
    @ExceptionHandler(Exception.class)
    public Result handle(Exception e){
        log.error("Exception: ", e);
        return Result.fail("系统内部错误");
    }

}
