package com.amadeus.mybatisdemo.mapper;

import com.amadeus.mybatisdemo.model.UserInfo;
import org.apache.ibatis.annotations.Options;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserInfoMapperTest {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Test
    void getList(){
        List<UserInfo> list = userInfoMapper.getList();
        list.stream().forEach(x-> System.out.println(x));
    }

    @Test
    void queryById() {
        UserInfo userInfo = userInfoMapper.queryById(3);
        System.out.println(userInfo);
    }

    @Test
    void insert() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("zhaoliu");
        userInfo.setPassword("zhaoliu");
        userInfo.setAge(19);
        userInfo.setPhone("11451419191810");
        Integer count = userInfoMapper.insert(userInfo);
        System.out.println("添加数据条数:" +count +", 数据ID:" + userInfo.getId());
    }

    @Test
    void delete() {
        Integer count = userInfoMapper.delete("zhaoliu");
        System.out.println("已删除数据条数:" + count);
    }

    @Test
    void selectByAge() {
        List<UserInfo> list = userInfoMapper.selectByAge(18);
        list.stream().forEach(userInfo -> System.out.println(userInfo));
    }

    @Test
    void queryAllUser() {
        List<UserInfo> list = userInfoMapper.queryAllUser();
        list.stream().forEach(userInfo -> System.out.println(userInfo));
    }
}