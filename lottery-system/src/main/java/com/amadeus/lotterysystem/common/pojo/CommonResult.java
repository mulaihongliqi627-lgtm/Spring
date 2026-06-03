package com.amadeus.lotterysystem.common.pojo;


import com.amadeus.lotterysystem.common.errorcode.ErrorCode;
import com.amadeus.lotterysystem.common.errorcode.GlobalErrorCodeConstants;
import lombok.Data;
import org.springframework.util.Assert;

@Data
public class CommonResult<T> {

    private Integer code;

    private T data;

    private String errorMsg;

    public static <T> CommonResult<T> success(T data){

        CommonResult<T>  result = new CommonResult<>();
        result.code = GlobalErrorCodeConstants.SUCCESS.getCode();
        result.data = data;
        result.errorMsg = "";
        return result;
    }

    public static <T> CommonResult<T> error(Integer code,String errorMsg){
        //确保传入的 code 参数不能是成功状态码（200）
        Assert.isTrue(!GlobalErrorCodeConstants.SUCCESS.getCode().equals(code),"code不是错误的异常");
        CommonResult<T>  result = new CommonResult<>();
        result.code = code;
        result.errorMsg = errorMsg;
        return result;
    }

    public static <T> CommonResult<T> error(ErrorCode errorCode){
        return error(errorCode.getCode(),errorCode.getMsg());
    }
}
