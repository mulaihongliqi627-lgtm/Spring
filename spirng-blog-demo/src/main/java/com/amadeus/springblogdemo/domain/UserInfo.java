package com.amadeus.springblogdemo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("user_info")
public class UserInfo {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String userName;

    private String password;

    private String githubUrl;

    private Integer deleteFlag;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
