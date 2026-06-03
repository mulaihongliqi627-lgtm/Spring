package com.amadeus.lotterysystem.service.Impl;

import com.amadeus.lotterysystem.dao.dataobject.Encrypt;
import com.amadeus.lotterysystem.dao.dataobject.UserDO;
import com.amadeus.lotterysystem.dao.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UserServiceImplTest {


    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserMapper userMapper;


    @Test
    void testPhoneisUserd(){

        String phoneNumber = "15451419198";

        Encrypt encryptPhone = new Encrypt(phoneNumber);
        long result = userMapper.selectCount(
                new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<UserDO>()
                        .eq(UserDO::getPhoneNumber, encryptPhone)
        );
        System.out.println(result);
    }
}