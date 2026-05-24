package com.amadeus.springblogdemo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("blog_info")
public class BlogInfo {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String title;

    private String content;

    private Integer userId;

    private Integer deleteFlag;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
