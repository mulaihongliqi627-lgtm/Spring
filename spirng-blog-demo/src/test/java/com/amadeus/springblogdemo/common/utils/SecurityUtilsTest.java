package com.amadeus.springblogdemo.common.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SecurityUtilsTest {

    @Test
    void encrypt() {
        String encrypt = SecurityUtils.encrypt("123456");
        System.out.println(encrypt);
    }
}