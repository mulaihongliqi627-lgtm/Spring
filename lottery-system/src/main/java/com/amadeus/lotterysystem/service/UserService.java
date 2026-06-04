package com.amadeus.lotterysystem.service;

import com.amadeus.lotterysystem.controller.param.UserLoginParam;
import com.amadeus.lotterysystem.controller.param.UserRegisterParam;
import com.amadeus.lotterysystem.controller.result.BaseUserInfoResult;
import com.amadeus.lotterysystem.service.dto.UserDTO;
import com.amadeus.lotterysystem.service.dto.UserLoginDTO;
import com.amadeus.lotterysystem.service.dto.UserRegisterDTO;
import com.amadeus.lotterysystem.service.enums.UserIdentityEnum;

import java.util.List;


public interface UserService {
    UserLoginDTO login(UserLoginParam param);

    UserRegisterDTO register(UserRegisterParam param);

    List<UserDTO> findUserInfoList(UserIdentityEnum identity);
}
