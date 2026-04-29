package com.amadeus.mybatisplusdemo.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserInfoMapperTest {

    @Autowired
    private UserInfoMapper userInfoMapper;

//    查询单个id数据
    @Test
    void selectById() {
        System.out.println(userInfoMapper.selectById(1));
    }


//    查询多个id数据
    @Test
    void selectByIds() {
        System.out.println(userInfoMapper.selectByIds(List.of(1,2)));
    }

}