package com.amadeus.lotterysystem.common.exception;

import com.amadeus.lotterysystem.common.errorcode.GlobalErrorCodeConstants;
import com.amadeus.lotterysystem.common.errorcode.ServiceErrorCodeConstants;
import com.amadeus.lotterysystem.common.pojo.CommonResult;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public CommonResult<?> handleServiceException(ServiceException ex) {
        return CommonResult.error(ex.getCode(), ex.getErrorMsg());
    }

    @ExceptionHandler(ControllerException.class)
    public CommonResult<?> handleControllerException(ControllerException ex) {
        return CommonResult.error(ex.getCode(), ex.getErrorMsg());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        String errorMsg = fieldError == null ? GlobalErrorCodeConstants.UNKNOWN.getMsg() : fieldError.getDefaultMessage();
        return CommonResult.error(GlobalErrorCodeConstants.UNKNOWN.getCode(), errorMsg);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public CommonResult<?> handleConstraintViolationException(ConstraintViolationException ex) {
        return CommonResult.error(GlobalErrorCodeConstants.UNKNOWN.getCode(), ex.getMessage());
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public CommonResult<?> handleDuplicateKeyException(DuplicateKeyException ex) {
        String message = ex.getMessage();
        if (message != null && message.contains("uk_phone_number")) {
            return CommonResult.error(ServiceErrorCodeConstants.PHONE_NUMBER_USED);
        }
        if (message != null && message.contains("uk_email")) {
            return CommonResult.error(ServiceErrorCodeConstants.MAIL_USED);
        }
        log.warn("Duplicate key exception", ex);
        return CommonResult.error(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public CommonResult<?> handleException(Exception ex) {
        log.error("Unexpected exception", ex);
        return CommonResult.error(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR);
    }
}
