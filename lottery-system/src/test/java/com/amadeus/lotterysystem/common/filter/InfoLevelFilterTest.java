package com.amadeus.lotterysystem.common.filter;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class InfoLevelFilterTest{

    private static final Logger logger = LoggerFactory.getLogger(InfoLevelFilterTest.class);
    
    @Test
    void test(){
        System.out.println("hello cheems");
        logger.info("hello cheems!");
    }
}