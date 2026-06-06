package com.amadeus.lotterysystem.dao.dataobject;

import com.amadeus.lotterysystem.service.enums.ActivityPrizeStatusEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
@TableName("activity_prize")
public class ActivityPrizeDO extends BaseDO {


    /**
     * 活动id
     */
    private Long activityId;

    /**
     * 奖品id
     */
    private Long prizeId;

    /**
     * 奖品数量
     */
    private Long prizeAmount;

    /**
     * 奖品等级
     */
    private String prizeTiers;

    /**
     * 奖品状态
     */
    private String status;

}
