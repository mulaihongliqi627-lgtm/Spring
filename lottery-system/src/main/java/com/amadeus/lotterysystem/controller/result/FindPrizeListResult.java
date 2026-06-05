package com.amadeus.lotterysystem.controller.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindPrizeListResult implements Serializable {

    /**
     * 总记录数
     */
    private Long total;


    /**
     * 当前页商品数据
     */
    private List<PrizeInfo> records;


    @Data
    public static class PrizeInfo implements Serializable {

        /**
         * 奖品id
         */
        private Long prizeId;

        /**
         * 名称
         */
        private String prizeName;

        /**
         * 描述
         */
        private String description;

        /**
         * 价值
         */
        private BigDecimal price;

        /**
         * 奖品图
         */
        private String imageUrl;

    }

}
