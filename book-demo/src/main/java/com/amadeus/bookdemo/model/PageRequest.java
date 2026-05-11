package com.amadeus.bookdemo.model;

public class PageRequest {
    //当前页
    private Integer currentPage = 1;
    //每页个数
    private Integer pageSize = 10;

    private int offset;

    public int getOffset() {
        return (currentPage-1) * pageSize;
    }
}
