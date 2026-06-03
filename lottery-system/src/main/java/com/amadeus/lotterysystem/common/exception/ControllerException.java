package com.amadeus.lotterysystem.common.exception;

import com.amadeus.lotterysystem.common.errorcode.ErrorCode;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ControllerException extends RuntimeException{

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误信息
     */
    private String errorMsg;

    public ControllerException(Integer code, String errorMsg) {
        this.code = code;
        this.errorMsg = errorMsg;
    }

    public ControllerException(ErrorCode errorCode){
        this.code = errorCode.getCode();
        this.errorMsg = errorCode.getMsg();
    }
}
