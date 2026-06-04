package com.amadeus.lotterysystem.controller;


import com.amadeus.lotterysystem.common.errorcode.ControllerErrorCodeConstants;
import com.amadeus.lotterysystem.common.exception.ControllerException;
import com.amadeus.lotterysystem.common.pojo.CommonResult;
import com.amadeus.lotterysystem.common.utils.JacksonUtil;
import com.amadeus.lotterysystem.controller.param.UserPasswordLogin;
import com.amadeus.lotterysystem.controller.result.BaseUserInfoResult;
import com.amadeus.lotterysystem.controller.result.UserLoginResult;
import com.amadeus.lotterysystem.controller.param.UserRegisterParam;
import com.amadeus.lotterysystem.controller.result.UserRegisterResult;
import com.amadeus.lotterysystem.service.UserService;
import com.amadeus.lotterysystem.service.dto.UserDTO;
import com.amadeus.lotterysystem.service.dto.UserLoginDTO;
import com.amadeus.lotterysystem.service.dto.UserRegisterDTO;
import com.amadeus.lotterysystem.service.enums.UserIdentityEnum;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


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


    @RequestMapping("/base-user/find-list")
    public CommonResult<List<BaseUserInfoResult>> findBaseUserInfoList(String identity){
        log.info("查询人员列表");
        List<UserDTO> userDTOList = userService.findUserInfoList(UserIdentityEnum.forName(identity));
        return CommonResult.success(converToList(userDTOList));
    }

    private List<BaseUserInfoResult> converToList(List<UserDTO> userDTOList) {
        if(CollectionUtils.isEmpty(userDTOList)){
            return Arrays.asList();
        }

        return  userDTOList.stream()
                .map(userDTO -> {
                    BaseUserInfoResult baseUserInfoResult = new BaseUserInfoResult();
                    baseUserInfoResult.setUserId(userDTO.getUserId());
                    baseUserInfoResult.setUserName(userDTO.getUserName());
                    baseUserInfoResult.setIdentity(userDTO.getIdentity().name());
                    return baseUserInfoResult;
                }) .collect(Collectors.toList());
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
