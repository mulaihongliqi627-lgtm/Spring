package com.amadeus.bookdemo.model;

import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Data
public class UserInfo {
    @Getter
    private Integer id;
    private String userName;
    private String password;
    private Integer deleteFlag;
    private Date createTime;
    private Date updateTime;
}
