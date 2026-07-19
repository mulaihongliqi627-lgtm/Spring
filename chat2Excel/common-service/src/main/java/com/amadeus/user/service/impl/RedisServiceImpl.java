package com.amadeus.user.service.impl;

import com.amadeus.user.service.RedisService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisServiceImpl implements RedisService {


    //String封装 的redis模版

    @Autowired
    private final StringRedisTemplate redisTemplate = new StringRedisTemplate();


    /**
     * 序列化工具
     */
    @Autowired
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 字符串模版前缀
     */

    /**
     * 验证码前缀
     */
    private static final String CODE_PREFIX = "code:";

    /**
     * 个人令牌前缀
     */
    private static final String TOKEN_PREFIX = "token:";

    /**
     * 老用户会话
     */
    private static final String USER_SESSION_PREFIX = "user_session:";


    /**
     * redis缓存验证码及用户信息
     * @param code 验证码
     * @param userId 用户 id
     * @param username 用户名称
     * @param expireSeconds 过期时间
     */
    @Override
    public void storeVerificationCode(String code, Long userId, String username, long expireSeconds) {
        String key = CODE_PREFIX + code;
        CodeInfo codeInfo = new CodeInfo(userId,username);
        try {
            //序列化codeInfo,写入redis
            String value = objectMapper.writeValueAsString(codeInfo);
            redisTemplate.opsForValue().set(key,value,expireSeconds, TimeUnit.SECONDS);
            log.info("[Redis缓存成功] 验证码{} , 用户名 {}",code,username);
        } catch (JsonProcessingException e) {
            log.error("[Redis缓存失败] 验证码{} , 用户名 {}",code,username);
        }
    }

    /**
     * 判断验证码是否存在
     * @param code 验证码
     * @return true: 验证码存在,仍然有效
     */
    @Override
    public boolean isCodeVaild(String code) {
        String key = CODE_PREFIX + code;
        return redisTemplate.hasKey(key);
    }

    /**
     * 删除redis中缓存的验证码
     * @param code
     */
    @Override
    public void moveCode(String code) {
        String key = CODE_PREFIX + code;
        redisTemplate.delete(key);
        log.info("已删除验证码{}",code);

    }

    /**
     * redis缓存的验证码用户信息
     */
    @Data
    @AllArgsConstructor
    private class CodeInfo{
        private Long userId;
        private String username;
    }

}
