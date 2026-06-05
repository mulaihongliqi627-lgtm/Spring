package com.amadeus.lotterysystem.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PrizeListDTO<T> {


    /**
     * 总记录数
     */
    private Long total;


    /**
     * 当前页商品数据
     */
    private List<T> records;
}
