package com.amadeus.bookdemo.model;

import com.amadeus.bookdemo.enums.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.ResultType;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result<T> {
    private int code;
    private String errMsg;
    private T data;


    public static Result unlogin(){
       return new Result(ResultCode.UNLOGIN.getCode(),"用户未登录",null);
    }


    public static <T> Result<T> Success(){
        return new Result(ResultCode.SUCCESS.getCode(), null,null);
    }
    public static <T> Result<T> success(T data){
        return new Result(ResultCode.SUCCESS.getCode(), null,data);
    }

    public static <T> Result fail(String errMsg){
        return new Result(ResultCode.FAIL.getCode(), errMsg, null);
    }

    public static <T> Result fail(String errMsg, T data){
        return new Result(ResultCode.FAIL.getCode(), errMsg, data);
    }

    public boolean isSuccess() {
        return this.code == ResultCode.SUCCESS.getCode();
    }

    public boolean isFail() {
        return this.code != ResultCode.SUCCESS.getCode();
    }
}
