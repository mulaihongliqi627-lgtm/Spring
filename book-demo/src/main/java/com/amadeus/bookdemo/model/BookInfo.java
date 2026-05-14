package com.amadeus.bookdemo.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class BookInfo {
    //图书ID
    private Integer id;
    //书名
    private String bookName;
    //作者
    private String author;
    //数量
    private Integer count;
    //定价
    private BigDecimal price; //金额也可以定义为long, 表示 分
    //出版社
    private String publish;
    //状态 0-无效 1-允许借阅   2-不允许借阅
    private Integer status;

    private String statusCN;

    private Date createTime;
    private Date updateTime;
}
