package com.amadeus.user.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

//统一封装响应
@AllArgsConstructor
public class Result<T> {

    /**
     * 响应码
     */
    private Integer code;


    /**
     * 响应信息
     */
    private String message;


    /**
     * 响应数据
     */
    private T data;


    @Getter
    public enum ResultCode{
        //成功
        SUCCESS(200,"操作成功"),

        //客户端错误
        BAD_REQUEST(400,"参数错误"),
        UNAUTHORIZED(401,"未授权"),
        NOT_FOUND(404,"资源不存在"),
        METHOD_NOT_ALLOWED(405,"不允许的方法"),
        REQUEST_TIMEOUT(408,"请求超时"),

        //服务端错误
        INTERNAL_SERVER_ERROR(500,"服务器错误"),
        BAD_GATEWAY(502,"网关错误"),
        SERVICE_UNAVAILABLE(503,"服务不可用"),
        GATEWAY_TIMEOUT(504,"网关超时");

        private final Integer code;
        private final String message;

        ResultCode(Integer code, String message){
            this.code = code;
            this.message = message;
        }
    };


    /**
     * 响应成功
     * @param message
     * @param data
     * @return
     * @param <T>
     */
    public static <T> Result<T> success(String message,T data){
        return  new Result<>(ResultCode.SUCCESS.getCode(),message,data);
    }

    /**
     * 响应失败
     * @param errorCode
     * @param message
     * @return
     * @param <T>
     */
    public static <T> Result<T> error(ResultCode errorCode,String message){
        return new Result<>(errorCode.getCode(),message,null);
    }


    /**
     * 请求参数异常
     * @param message
     * @return
     * @param <T>
     */
    public static <T> Result<T> badRequest(String message){
        return new Result<>(ResultCode.BAD_REQUEST.getCode(),message,null);
    }

    /**
     * 服务端异常
     * @param message
     * @return
     * @param <T>
     */
    public static <T> Result<T> serverError(String message){
        return new Result<>(ResultCode.INTERNAL_SERVER_ERROR.getCode(),message,null);
    }



}
