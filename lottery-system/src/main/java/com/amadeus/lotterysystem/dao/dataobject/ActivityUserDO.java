package com.amadeus.lotterysystem.dao.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("activity_user")
public class ActivityUserDO extends BaseDO{

    /**
     * 活动id
     */
    private Long activityId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户状态
     */
    private String status;
}
