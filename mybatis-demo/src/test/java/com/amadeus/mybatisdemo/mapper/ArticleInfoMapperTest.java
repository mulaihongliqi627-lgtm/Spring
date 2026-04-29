package com.amadeus.mybatisdemo.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArticleInfoMapperTest {


    @Autowired
    private ArticleInfoMapper articleInfoMapper;

    @Test
    void queryArticleInfoById() {
        System.out.println(articleInfoMapper.queryArticleInfoById(1));

    }
}