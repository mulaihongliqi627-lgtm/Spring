package com.amadeus.lotterysystem.dao.mapper;

import com.amadeus.lotterysystem.dao.dataobject.ActivityDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jakarta.validation.constraints.NotBlank;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface ActivityMapper extends BaseMapper<ActivityDO> {



}
