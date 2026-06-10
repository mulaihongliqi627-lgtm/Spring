package com.amadeus.lotterysystem.dao.mapper;

import com.amadeus.lotterysystem.dao.dataobject.WinningRecordDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface WinningRecordMapper extends BaseMapper<WinningRecordDO> {

    @Select("select count(*) from winning_record where activity_id = #{activityId} and prize_id = #{prizeId}")
    int countByAPId(@Param("activityId") Long activityId,
                    @Param("prizeId") Long prizeId);

    @Delete("delete from winning_record where activity_id = #{activityId} and prize_id = #{prizeId}")
    int deleteByAPId(@Param("activityId") Long activityId,
                     @Param("prizeId") Long prizeId);
}
