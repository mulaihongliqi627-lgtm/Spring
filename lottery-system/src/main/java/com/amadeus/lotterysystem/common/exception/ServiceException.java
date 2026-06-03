package com.amadeus.lotterysystem.common.exception;

import com.amadeus.lotterysystem.common.errorcode.ErrorCode;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ServiceException extends RuntimeException{

    private Integer code;

    private String errorMsg;

    public ServiceException(Integer code, String errorMsg) {
        this.code = code;
        this.errorMsg = errorMsg;
    }

    public ServiceException(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.errorMsg = errorCode.getMsg();
    }

}
