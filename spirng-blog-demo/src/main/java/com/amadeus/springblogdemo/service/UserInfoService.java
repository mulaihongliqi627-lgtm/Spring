package com.amadeus.springblogdemo.service;

import com.amadeus.springblogdemo.domain.UserInfo;
import com.amadeus.springblogdemo.pojo.request.UserLoginRequest;
import com.amadeus.springblogdemo.pojo.request.UserRegisterRequest;
import com.amadeus.springblogdemo.pojo.response.UserInfoResponse;
import com.amadeus.springblogdemo.pojo.response.UserLoginResponse;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserInfoService extends IService<UserInfo> {

    UserLoginResponse login(UserLoginRequest request);

    UserInfoResponse getUserInfo(Integer userId);

    UserInfoResponse getAuthorInfo(Integer blogId);

    UserLoginResponse register(UserRegisterRequest request);
}
