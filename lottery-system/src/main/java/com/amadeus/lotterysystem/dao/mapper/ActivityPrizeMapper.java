package com.amadeus.lotterysystem.dao.mapper;

import com.amadeus.lotterysystem.dao.dataobject.ActivityPrizeDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jakarta.validation.constraints.NotNull;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface ActivityPrizeMapper extends BaseMapper<ActivityPrizeDO> {


    @Select("select * from activity_prize where activity_id = #{activityId} and prize_id = #{prizeId}")
    ActivityPrizeDO selectByAPId(@NotNull(message = "活动id不能为空") @Param("activityId") Long activityId,
                                 @NotNull(message = "奖品id不能为空") @Param("prizeId") Long prizeId);
}
