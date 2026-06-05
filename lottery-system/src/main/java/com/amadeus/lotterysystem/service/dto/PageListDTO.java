package com.amadeus.lotterysystem.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: yibo
 */

@NoArgsConstructor
@Data
public class PageListDTO<T> {

    /**
     * 总量
     */
    private Long total;

    /**
     * 当前页列表
     */
    private List<T> records;


    public PageListDTO(Long total, List<T> records) {
        this.total = total;
        this.records = records;
    }


}

