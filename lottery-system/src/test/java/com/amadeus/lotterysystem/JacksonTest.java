package com.amadeus.lotterysystem;


import com.amadeus.lotterysystem.common.pojo.CommonResult;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class JacksonTest {

    @Test
    void jacksonTest() {

        ObjectMapper objectMapper = new ObjectMapper();

        CommonResult<String> result = CommonResult.error(500, "系统错误");
        String str;
    }




}
