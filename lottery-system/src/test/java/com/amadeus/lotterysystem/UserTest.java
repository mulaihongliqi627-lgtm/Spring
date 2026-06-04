package com.amadeus.lotterysystem;


import com.amadeus.lotterysystem.dao.dataobject.UserDO;
import com.amadeus.lotterysystem.dao.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void findUserInfoList(){
        //查询出所有身份的用户
//        List<UserDO> userDOList = userMapper.selectUserListByIdentity(null);
//        for(UserDO userDO: userDOList){
//            System.out.println(userDO.getId() + " " + userDO.getUserName() + " " + userDO.getIdentity());
//        }


        //查询出所有普通用户
        List<UserDO> userDOList = userMapper.selectUserListByIdentity("normal");
        for(UserDO userDO: userDOList){
            System.out.println(userDO.getId() + " " + userDO.getUserName() + " " + userDO.getIdentity());
        }

    }
}
