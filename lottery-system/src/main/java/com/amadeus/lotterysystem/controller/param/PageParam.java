package com.amadeus.lotterysystem.controller.param;

import lombok.Data;

import java.io.Serializable;


@Data
public class PageParam implements Serializable {

    /**
     * 当前页
     */
    private Integer currentPage;


    /**
     * 每一页中的记录个数
     */
    private Integer pageSize;

    //计算偏移量
    public Integer offest(){
        return (currentPage - 1) * pageSize;
    }
}
