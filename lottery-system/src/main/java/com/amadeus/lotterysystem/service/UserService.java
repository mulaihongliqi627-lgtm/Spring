package com.amadeus.lotterysystem.service;


import com.amadeus.lotterysystem.controller.param.UserRegisterParam;
import com.amadeus.lotterysystem.service.dto.UserRegisterDTO;
import org.springframework.stereotype.Service;


public interface UserService {
    UserRegisterDTO register(UserRegisterParam param);

}
