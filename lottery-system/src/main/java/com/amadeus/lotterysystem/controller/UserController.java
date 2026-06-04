package com.amadeus.lotterysystem.controller;


import com.amadeus.lotterysystem.common.errorcode.ControllerErrorCodeConstants;
import com.amadeus.lotterysystem.common.exception.ControllerException;
import com.amadeus.lotterysystem.common.pojo.CommonResult;
import com.amadeus.lotterysystem.common.utils.JacksonUtil;
import com.amadeus.lotterysystem.controller.param.UserPasswordLogin;
import com.amadeus.lotterysystem.controller.result.UserLoginResult;
import com.amadeus.lotterysystem.controller.param.UserRegisterParam;
import com.amadeus.lotterysystem.controller.result.UserRegisterResult;
import com.amadeus.lotterysystem.service.UserService;
import com.amadeus.lotterysystem.service.dto.UserLoginDTO;
import com.amadeus.lotterysystem.service.dto.UserRegisterDTO;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public CommonResult<UserRegisterResult> userRegister(
            @Validated @RequestBody UserRegisterParam param){

        log.info("用户注册请求 , parm {}", JacksonUtil.writeValueAsString( param));
        UserRegisterDTO userRegisterDTO = userService.register(param);
        return CommonResult.success(convertToUserRegisterResult(userRegisterDTO));
    }


    @PostMapping("/password/login")
    public CommonResult<UserLoginResult> userPasswordLogin(@Validated @RequestBody UserPasswordLogin param){
        log.info("用户密码登录请求 , parm {}", JacksonUtil.writeValueAsString( param));
        UserLoginDTO userLoginDTO = userService.login(param);
        return CommonResult.success(converToUserLoginResult(userLoginDTO));
    }

    private UserRegisterResult convertToUserRegisterResult(UserRegisterDTO userRegisterDTO) {
        if(null == userRegisterDTO){
            throw new ControllerException(ControllerErrorCodeConstants.REGISTER_ERROR);
        }
        return new UserRegisterResult(userRegisterDTO.getUserId());
    }

    private UserLoginResult converToUserLoginResult(UserLoginDTO userLoginDTO){
        if(null == userLoginDTO){
            throw  new ControllerException(ControllerErrorCodeConstants.LOGIN_ERROR);
        }
        return new UserLoginResult(userLoginDTO.getToken(),userLoginDTO.getIdentity());

    }
}
