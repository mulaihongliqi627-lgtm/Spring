package com.amadeus.lotterysystem.common.errorcode;

public interface ControllerErrorCodeConstants {

    //人员模块错误码

    ErrorCode REGISTER_ERROR = new ErrorCode(100, "用户注册失败");
    ErrorCode LOGIN_ERROR = new ErrorCode(101, "用户登录失败");
}
