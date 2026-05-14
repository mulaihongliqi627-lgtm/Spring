package com.amadeus.bookdemo.model;

import lombok.Data;

@Data
public class PageRequest {
    //当前页
    private Integer currentPage = 1;
    //每页个数
    private Integer pageSize = 10;

    public int getOffset() {
        return (currentPage-1) * pageSize;
    }
}
