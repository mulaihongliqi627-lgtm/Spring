package com.amadeus.lotterysystem;


import cn.hutool.crypto.SecureUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;

@SpringBootTest
public class TestEnctry {

    //准备密钥,注意密钥需要和加密时使用的密钥保持一致
    private final byte[] KEY = "123456789abcdefg".getBytes(StandardCharsets.UTF_8);

    //测试解密数据
    @Test
    public void decrypt(){
        String encryptData = "2d1018b91ead18cc8b649a3da062ea8f";
        String decryptData = SecureUtil.aes(KEY).decryptStr(encryptData);
        System.out.println("解密结果：" + decryptData);
    }
}
