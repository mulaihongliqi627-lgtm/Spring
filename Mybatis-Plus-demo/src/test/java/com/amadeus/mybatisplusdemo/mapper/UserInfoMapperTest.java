package com.amadeus.mybatisplusdemo.mapper;

import com.amadeus.mybatisplusdemo.model.Userinfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.apache.catalina.User;
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

    @Test
    void queryWrapper(){
        QueryWrapper<Userinfo> queryWrapper = new QueryWrapper<Userinfo>()
                .select("id","username","password","age")
                .eq("age",18)
                .like("username","min");

        List<Userinfo> userinfos = userInfoMapper.selectList(queryWrapper);
        System.out.println(userinfos);
    }

    @Test
    void UpdateWrapper(){
        UpdateWrapper<Userinfo> updateWrapper = new UpdateWrapper<Userinfo>()
                .set("gender",0)
                .set("age",20)
                .in("id",List.of(1,2,4));
        userInfoMapper.update(updateWrapper);
    }



    @Test
    void deleteWrapper() {
        QueryWrapper<Userinfo> queryWrapper = new QueryWrapper<Userinfo>()
                .eq("id",4);
        int row = userInfoMapper.delete(queryWrapper);
        System.out.println("删除的行数: " + row);
    }

    @Test
    void insertUserInfo(){
        Userinfo userinfo = new Userinfo();
        userinfo.setUsername("wangwu");
        userinfo.setPassword("wangwu");
        userinfo.setAge(18);
        userinfo.setPhone("1145141919");
        userInfoMapper.insert(userinfo);
        System.out.println(userinfo.getUsername());

    }

    @Test
    void updateUserInfo(){
        UpdateWrapper<Userinfo> updateWrapper = new UpdateWrapper<Userinfo>()
                .set("id",4)
                .eq("username","wangwu");
        userInfoMapper.update(updateWrapper);
    }

    @Test
    void queryByUsername(){
        QueryWrapper<Userinfo> queryWrapper = new QueryWrapper<Userinfo>()
                .eq("username","zhangsan");
        List<Userinfo> userinfos = userInfoMapper.selectList(queryWrapper);
        System.out.println(userinfos);
    }


    //使用lambdaWrapper查询id为3的用户信息
    @Test
    void lambdaQueryWrapper(){
        QueryWrapper<Userinfo> queryWrapper = new QueryWrapper<Userinfo>();
        queryWrapper.lambda()
                .select(Userinfo::getUsername,Userinfo::getPassword,Userinfo::getAge,Userinfo::getGender,Userinfo::getPhone)
                .eq(Userinfo::getId,3);
        System.out.println(userInfoMapper.selectList(queryWrapper));
    }

    //使用lambdaWrapper更新id为3的用户信息
    @Test
    void lambdaUpdateWrapper(){
        UpdateWrapper<Userinfo> updateWrapper = new UpdateWrapper<Userinfo>();
        updateWrapper.lambda()
                .set(Userinfo::getGender,1)
                .set(Userinfo::getAge,16)
                .eq(Userinfo::getId,3);
        userInfoMapper.update(updateWrapper);
    }


    @Test
    void updateAgeByIdWrapper() {
        //1.先查找到指定id的用户信息
        QueryWrapper<Userinfo> queryWrapper = new QueryWrapper<Userinfo>()
                .eq("id", 3);
        //2.更新age
        int rows= userInfoMapper.updateAgeByIdWrapper(18,queryWrapper);
    }
}