package com.amadeus.lotterysystem.service.Impl;


import com.amadeus.lotterysystem.common.exception.ServiceException;
import com.amadeus.lotterysystem.controller.param.UserRegisterParam;
import com.amadeus.lotterysystem.dao.dataobject.Encrypt;
import com.amadeus.lotterysystem.dao.dataobject.UserDO;
import com.amadeus.lotterysystem.dao.mapper.UserMapper;
import generator.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserUniquenessTest {


    @Autowired
    private UserMapper userMapper;

    @Test
    void phoneNumberUniquenessTest() {

//        UserDO = userMapper.selectByPhoneNumber("15451419198");
//
//        if(userDO != null){
//            System.out.println("手机号已存在");
//        }
        //再次尝试注册一个相同手机号的用户
        try{
            UserDO param = new UserDO();
            param.setUserName("张三");
            param.setEmail("100001@qq.com");
            param.setPhoneNumber(new Encrypt("15451419198"));//和数据库中存储的手机号保持一致
            param.setPassword("11111");
            param.setIdentity("USER");
            userMapper.insert(param);
        }catch (ServiceException e){
            System.out.println("手机号已存在");
        }

    }

}
