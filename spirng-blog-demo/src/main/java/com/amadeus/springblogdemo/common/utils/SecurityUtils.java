package com.amadeus.springblogdemo.common.utils;

import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class SecurityUtils {
    /**
     * 加密
     * @param password  用户输入的密码
     * @return  盐值+密文
     */
    public static String encrypt(String password){
        //盐值
        String salt = UUID.randomUUID().toString().replace("-", "");
        //md5(盐值+明文) -> 密文
        String finalPassword = DigestUtils.md5DigestAsHex((salt + password).getBytes(StandardCharsets.UTF_8));  //密文
        //数据库中存储:  盐值+密文
        return salt+finalPassword;
    }

    /**
     * 密码验证
     * @param inputPassword 用户输入的密码
     * @param sqlPassword 数据库中存储的密码
     * @return
     */
    public static boolean verify(String inputPassword, String sqlPassword){
        if (!StringUtils.hasText(inputPassword) || !StringUtils.hasText(sqlPassword)){
            return false;
        }
        if (sqlPassword.length()!=64){
            return false;
        }
        //盐值
        String salt = sqlPassword.substring(0, 32);
        //md5(盐值+明文) -> 密文
        String finalPassword = DigestUtils.md5DigestAsHex((salt + inputPassword).getBytes(StandardCharsets.UTF_8));  //密文
        //数据库中存储:  盐值+密文
        return sqlPassword.equals(salt+finalPassword);
    }
}
