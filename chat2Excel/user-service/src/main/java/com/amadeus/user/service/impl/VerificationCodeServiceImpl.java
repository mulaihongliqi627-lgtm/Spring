package com.amadeus.user.service.impl;

import com.amadeus.user.entity.UserEntity;
import com.amadeus.user.mapper.UserMapper;
import com.amadeus.user.service.EmaiService;
import com.amadeus.user.service.RedisService;
import com.amadeus.user.service.VerificationCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class VerificationCodeServiceImpl implements VerificationCodeService{


    @Autowired
    private EmaiService emaiService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public int sendCode(String email) {
        //1.生成验证码
        String code = String.valueOf(Math.random()*9000000 + 1000000);

        //2.发送验证码
        boolean result = emaiService.sendVerificationCode(email, code);


        //3.依据邮箱查询数据库,判断是否登录
        UserEntity user = userMapper.findByLoginKey(email);

        //用户不存在,注册
        if(null == user){
            user = new UserEntity();
            user.setEmail(email);
            user.setId(0L);
        }else {
            //用户存在,验证码缓存到redis
            //缓存验证码, 过期时间 : 300s
            redisService.storeVerificationCode(code,user.getId(),user.getUserName(),300L);
        }

        //4.判断验证码是否发送成功
        if(result){
            log.info("发送验证码成功：{}", code);
        }else{
            log.error("发送验证码失败");
            return -1;
        }
        return 0;
    }
}
