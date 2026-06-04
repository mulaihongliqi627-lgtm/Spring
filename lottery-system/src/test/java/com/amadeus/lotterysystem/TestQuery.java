package com.amadeus.lotterysystem;


import com.amadeus.lotterysystem.dao.dataobject.Encrypt;
import com.amadeus.lotterysystem.dao.dataobject.UserDO;
import com.amadeus.lotterysystem.dao.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestQuery {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void queryByMail(){
        String mail = "10086@qq.com";
        UserDO user = userMapper.selectByEmail(mail);
        System.out.println(user.toString());
    }


    @Test
    public void queryByPhoneNumber(){
        Encrypt phoneNumber = new Encrypt("15290229881");
        UserDO user = userMapper.selectByPhoneNumber(phoneNumber);
        if(null == user){
            System.out.println("查询不到该手机号对应的用户!");
        }else{
            System.out.println(user.toString());
        }
    }
}
