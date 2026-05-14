package com.amadeus.bookdemo.advice;

import com.amadeus.bookdemo.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@ResponseBody
@ControllerAdvice
public class ErrorAdvice {

    @ExceptionHandler
    public Result exceptionHandler(Exception e){
        log.error("异常信息:{}", e);
        return  Result.fail("服务器异常");
    }

    @ExceptionHandler
    public Result exceptionHandler(NoResourceFoundException e){
        log.error("异常信息:{}", e);
        return  Result.fail("资源不存在" + e.getResourcePath());
    }
}
