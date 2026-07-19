package com.amadeus.user.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogOperation {

    /**
     * 操作描述，通常⽤于标明接⼝的业务含义，例如“新增⽤⼾”“发送验证码”等。
     */
    String value() default "";


    /**
     * 是否记录请求参数。
     */
    boolean logRequest() default true;

    /**
     * 是否记录响应结果。
     */
    boolean logResponse() default true;

    /**
     * 是否记录⽅法执⾏耗时，便于分析接⼝性能瓶颈。
     */
    boolean logExecutionTime() default true;


    /**
     * 敏感字段，用于过滤掉请求参数中的敏感字段。
     */
    String[] sensitiveFields() default {};
}
