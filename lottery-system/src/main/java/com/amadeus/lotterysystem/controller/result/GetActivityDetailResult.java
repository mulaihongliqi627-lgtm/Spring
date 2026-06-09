package com.amadeus.lotterysystem.controller.result;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class GetActivityDetailResult implements Serializable {

    private Long activityId;

    private String activityName;

    private String desc;

    private Boolean valid;

    private List<PrizeInfo> prizeList;

    private List<UserInfo> userList;

    @Data
    public static class PrizeInfo implements Serializable {

        private Long prizeId;

        private String name;

        private String imageUrl;

        private BigDecimal price;

        private String description;

        private String tiers;

        private Long prizeAmount;

        private Boolean valid;
    }

    @Data
    public static class UserInfo implements Serializable {

        private Long userId;

        private String userName;

        private Boolean valid;
    }
}
