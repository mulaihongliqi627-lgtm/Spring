package com.amadeus.mybatisplusdemo.mapper;

import com.amadeus.mybatisplusdemo.model.Userinfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInfoMapper extends BaseMapper<Userinfo> {

}
