package com.amadeus.user.service;

public interface RedisService {


    /**
     * redis 缓存验证码和人员信息
     * @param code 验证码
     * @param userId 用户 id
     * @param username 用户名称
     * @param expireSeconds 过期时间
     */
    void storeVerificationCode(String code,Long userId,String username,long expireSeconds);


    /**
     * 验证验证码是否有效
     * @param code 验证码
     * @return true 验证码仍然存在并未过期
     */
    boolean isCodeVaild(String code);

    /**
     * 删除验证码
     * @param code
     */
    void moveCode(String code);
}
