package com.amadeus.springblogdemo.pojo.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BlogInfoResponse {

    private Integer id;

    private String title;

    private String content;

    private Integer userId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
