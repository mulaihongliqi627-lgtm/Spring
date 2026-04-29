package com.amadeus.bookdemo.service;

import com.amadeus.bookdemo.constant.Constants;
import com.amadeus.bookdemo.mapper.UserInfoMapper;
import com.amadeus.bookdemo.model.UserInfo;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserInfoMapper userInfoMapper;

//    校验登录是否可以成功
    public boolean checkPassword(String name, String password, HttpSession session) {
        //从数据库中获取对应name的用户信息
        UserInfo userInfo = userInfoMapper.queryUserInfoByName(name);
        if(userInfo == null || userInfo.getId() <= 0){
            log.warn("用户" + name + "不存在!");
            return false;
        }

        boolean checkPassword = password.equals(userInfo.getPassword());
        if(!checkPassword){
            log.info("密码错误!");
            return false;
        }else{
            //清空密码,session中只存储无password的用户信息
            userInfo.setPassword("");
            session.setAttribute(Constants.SESSION_USER_KEY,userInfo);
            return true;
        }
    }
}
