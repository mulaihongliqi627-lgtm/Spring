package com.amadeus.springblogdemo.controller;


import com.amadeus.springblogdemo.pojo.request.UserLoginRequest;
import com.amadeus.springblogdemo.pojo.response.Result;
import com.amadeus.springblogdemo.pojo.response.UserInfoResponse;
import com.amadeus.springblogdemo.pojo.response.UserLoginResponse;
import com.amadeus.springblogdemo.service.UserInfoService;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserInfoService userInfoService;
    @RequestMapping("/login")
    public UserLoginResponse login(@Validated @RequestBody UserLoginRequest request){
        log.info("用户登录,userName :{}",request.getUserName());
        return userInfoService.login(request);
    }


    /**
     * 根据用户ID查询用户信息
     * @param userId
     * @return UserInfoResponse
     */

    @RequestMapping("/getUserInfo")
    public UserInfoResponse getUserInfo(@NotNull Integer userId){
        log.info("获取用户信息,userId :{}",userId);
        return userInfoService.getUserInfo(userId);
    }

    /**
     *按照blogID查询作者信息
     * @param blogId
     * @return UserInfoResponse
     */


    @RequestMapping("/getAuthorInfo")
    public UserInfoResponse getAuthorInfo(@NotNull Integer blogId){
        log.info("获取作者信息,blogId :{}",blogId);
        return userInfoService.getAuthorInfo(blogId);
    }
}
