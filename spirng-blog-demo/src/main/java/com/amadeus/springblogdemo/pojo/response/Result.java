package com.amadeus.springblogdemo.pojo.response;

import lombok.Data;

@Data
public class Result<T> {

    private Integer code;

    private String message;

    private T data;

    public static <T> Result<T> ok(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("ok");
        result.setData(data);
        return result;
    }

    public static <T> Result<T> fail(String message) {
        Result<T> result = new Result<>();
        result.setCode(500);
        result.setMessage(message);
        return result;
    }
}
