package com.amadeus.lotterysystem.service.dto;

import com.amadeus.lotterysystem.service.enums.ActivityPrizeStatusEnum;
import com.amadeus.lotterysystem.service.enums.ActivityStatusEnum;
import com.amadeus.lotterysystem.service.enums.ActivityUserStatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class ConvertActivityStatusDTO implements Serializable {

    /**
     * 活动id
     */
    private Long activityId;

    /**
     * 活动目标状态
     */
    private ActivityStatusEnum targetActivityStatus;

    /**
     * 奖品id
     */
    private Long prizeId;

    /**
     * 奖品目标状态
     */
    private ActivityPrizeStatusEnum targetPrizeStatus;

    /**
     * 人员id列表
     */
    private List<Long> userIds;

    /**
     * 人员目标状态
     */
    private ActivityUserStatusEnum targetUserStatus;
}
