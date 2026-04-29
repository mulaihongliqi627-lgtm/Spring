package com.amadeus.bookdemo.model;

import com.amadeus.bookdemo.enums.ResultCode;
import lombok.Data;

@Data
public class Result<T> {
    private int code;
    private String errMsg;
    private T data;

    public static Result unlogin(){
        Result result = new Result();
        result.setCode(ResultCode.UNLOGIN.getCode());
        result.setErrMsg("用户未登录");
        return result;
    }

    public static <T> Result success(T data){
        Result result = new Result();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setErrMsg("");
        result.setData(data);
        return result;
    }

    public static <T> Result fail(String errMsg){
        Result result = new Result();
        result.setCode(ResultCode.FAIL.getCode());
        result.setErrMsg(errMsg);
        return result;
    }

    public static <T> Result fail(String errMsg, T data){
        Result result = new Result();
        result.setCode(ResultCode.FAIL.getCode());
        result.setErrMsg(errMsg);
        result.setData(data);
        return result;
    }
}
