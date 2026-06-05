package com.amadeus.lotterysystem.dao.mapper;

import com.amadeus.lotterysystem.dao.dataobject.PrizeDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PrizeMapper extends BaseMapper<PrizeDO> {

    /**
     * 查询奖品数量
     * @return
     */
    @Select("select count(*) from prize")
    int count();

    @Select("select id, gmt_create, gmt_modified, name, description, price, image_url from prize limit #{offset}, #{pageSize}")
    List<PrizeDO> findPrizeList(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize);
}
