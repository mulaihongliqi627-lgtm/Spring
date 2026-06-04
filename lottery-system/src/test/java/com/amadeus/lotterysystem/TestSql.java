package com.amadeus.lotterysystem;


import com.amadeus.lotterysystem.dao.dataobject.UserDO;
import com.amadeus.lotterysystem.dao.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestSql {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testDelById(){
        int count = userMapper.deleteById(2061688033626349570L);
        int count2 = userMapper.deleteById(2062046654608482305L);
    }
}
