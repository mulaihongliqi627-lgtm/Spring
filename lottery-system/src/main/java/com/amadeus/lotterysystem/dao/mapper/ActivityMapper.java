package com.amadeus.lotterysystem.dao.mapper;

import com.amadeus.lotterysystem.dao.dataobject.ActivityDO;
import com.amadeus.lotterysystem.service.dto.ActivityDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jakarta.validation.constraints.NotBlank;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface ActivityMapper extends BaseMapper<ActivityDO> {


    @Select("select * from activity order by id desc limit #{offset}, #{pageSize}")
    List<ActivityDO> selectActivityList(@Param("offset") Integer offset,
                                        @Param("pageSize") Integer pageSize);
}
