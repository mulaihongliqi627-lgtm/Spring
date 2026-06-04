package com.amadeus.lotterysystem.service;

import com.amadeus.lotterysystem.controller.param.UserLoginParam;
import com.amadeus.lotterysystem.controller.param.UserRegisterParam;
import com.amadeus.lotterysystem.service.dto.UserLoginDTO;
import com.amadeus.lotterysystem.service.dto.UserRegisterDTO;


public interface UserService {
    UserLoginDTO login(UserLoginParam param);

    UserRegisterDTO register(UserRegisterParam param);

}
