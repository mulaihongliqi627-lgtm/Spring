package com.amadeus.springblogdemo.common.advice;


import com.amadeus.springblogdemo.common.exception.BlogException;
import com.amadeus.springblogdemo.pojo.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {
    
    @ExceptionHandler(BlogException.class)
    public Result handle(BlogException e){
        log.error("BlogException: ", e);
        return Result.fail(e.getMessage());
    }
    
    @ExceptionHandler(Exception.class)
    public Result handle(Exception e){
        log.error("Exception: ", e);
        return Result.fail("系统内部错误");
    }

}
