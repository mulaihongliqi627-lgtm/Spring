package com.amadeus.lotterysystem.controller.param;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class DrawPrizeParam implements Serializable {


    /**
     * 活动id
     */
    @NotNull(message = "活动id不能为空")
    private Long activityId;


    /**
     * 奖品id
     */
    @NotNull(message = "奖品id不能为空")
    private Long prizeId;

    /**
     * 奖品等级
     */
    @NotBlank(message = "奖品等级不能为空")
    private String prizeTiers;


    /**
     * 中奖时间
     */
    @NotNull(message = "中奖时间不能为空")
    private Date winningTime;


    /**
     * 中奖人员列表
     */
    @NotEmpty(message = "中奖人员列表不能为空")
    private List<Winner> winnerList;


    @Data
    public static class Winner{

        /**
         * 中奖人员id
         */
        @NotNull(message = "中奖人员id不能为空")
        private Long userId;

        /**
         * 中奖人员姓名
         */
        @NotBlank(message = "中奖人员姓名不能为空")
        private String userName;
    }

}
