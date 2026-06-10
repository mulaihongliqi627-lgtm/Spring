package com.amadeus.lotterysystem.common.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class MailUtilTest {

    @Autowired
    private MailUtil mailUtil;


    @Test
    void sendSampleMail() {

        String to = "3440324269@qq.com";
        String subject = "测试邮件";
        String context = "测试邮件内容";
        mailUtil.sendSampleMail(to, subject, context);
    }
}