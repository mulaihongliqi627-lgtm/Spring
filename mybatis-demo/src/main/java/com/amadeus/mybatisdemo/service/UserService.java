package com.amadeus.mybatisdemo.service;

import com.amadeus.mybatisdemo.mapper.UserInfoMapper;
import com.amadeus.mybatisdemo.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    //service层调用数据层dao/mapper
    @Autowired
    private UserInfoMapper userInfoMapper;
    public List<UserInfo> getList() {
        return userInfoMapper.getList();
    }

    public List<UserInfo> queryAllUser(){
        return userInfoMapper.queryAllUser();
    }
}
