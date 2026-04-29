package com.amadeus.mybatisdemo.mapper;

import com.amadeus.mybatisdemo.model.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLOutput;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserInfoMapperXMLTest {


    @Autowired
    private UserInfoMapperXML userInfoMapperXML;

    @Test
    void getList() {
        System.out.println(userInfoMapperXML.getList());
    }


    @Test
    void queryAllUser() {
        System.out.println(userInfoMapperXML.queryAllUser());
    }

    @Test
    void queryById() {
        UserInfo userInfo = userInfoMapperXML.queryById(3);
        System.out.println(userInfo);
    }

    @Test
    void insert() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("test_user");
        userInfo.setPassword("test_password");
        userInfo.setAge(20);
        userInfo.setGender(1);
        userInfo.setPhone("12345678901");
        Integer count = userInfoMapperXML.insert(userInfo);
        System.out.println("添加数据条数:" + count + ", 数据ID:" + userInfo.getId());
    }

    @Test
    void delete() {
        Integer count = userInfoMapperXML.delete("test_user");
        System.out.println("已删除数据条数:" + count);
    }

    @Test
    void selectByAge() {
        System.out.println(userInfoMapperXML.selectByAge(18));
    }

    @Test
    void updateUsernameById() {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(4);
        userInfo.setUsername("cheems");
        System.out.println(userInfoMapperXML.updateUsernameById(userInfo));
    }

    @Test
    void queryByUsername() {
        System.out.println(userInfoMapperXML.queryByUsername("cheems"));
    }

    @Test
    void queryAllSort() {
        System.out.println(userInfoMapperXML.queryAllSort("desc"));
    }

    @Test
    void queryAllUserByLike() {
        System.out.println(userInfoMapperXML.queryAllUserByLike("ee"));
    }
}
