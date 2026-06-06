package com.amadeus.lotterysystem;


import com.amadeus.lotterysystem.common.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
public class RedisTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisUtil redisUtil;

    @Test
    void redisTest(){
        stringRedisTemplate.opsForValue().set("name","cheems");
        System.out.println("从redis中获取值" + stringRedisTemplate.opsForValue().get("name"));
    }

    @Test
    void redisTest2(){
        System.out.println(redisUtil.hasKey("name"));
        System.out.println(redisUtil.get("name"));
        redisUtil.delKey("name");
        redisUtil.hasKey("name");
        System.out.println("------------------");
        redisUtil.set("amadeus","kurisu",10);
        System.out.println(redisUtil.get("amadeus"));
    }
}
