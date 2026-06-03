package com.amadeus.lotterysystem.service.Impl;

import com.amadeus.lotterysystem.dao.dataobject.Encrypt;
import com.amadeus.lotterysystem.dao.dataobject.UserDO;
import com.amadeus.lotterysystem.dao.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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

    /**
     * 获取数据库中存储的手机号明文信息
     */

    @Test
    void TestGetPhoneNumber(){
        Long userId = 2060995077940490241L;
        UserDO userDO = userMapper.selectById(userId);
        
        System.out.println("用户ID: " + userDO.getId());
        System.out.println("用户名: " + userDO.getUserName());
        System.out.println("手机号对象: " + userDO.getPhoneNumber());
        
        if (userDO.getPhoneNumber() != null) {
            String phoneNumber = userDO.getPhoneNumber().getValue();
            System.out.println("手机号明文: " + phoneNumber);
        } else {
            System.out.println("警告: 手机号字段为null，请检查数据库中该用户的phone_number字段是否有值");
        }
    }
}