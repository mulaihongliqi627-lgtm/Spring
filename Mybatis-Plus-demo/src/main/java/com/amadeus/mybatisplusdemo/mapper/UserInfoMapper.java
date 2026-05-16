package com.amadeus.mybatisplusdemo.mapper;

import com.amadeus.mybatisplusdemo.model.Userinfo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserInfoMapper extends BaseMapper<Userinfo> {

    @Select("select id,username,password,age from user_info where age = 18 and username like '%min%'")
    List<Userinfo> queryByCondition();


    @Select("select id, username, password, age from user_info ${ew.customSqlSegment}")
    List<Userinfo> queryUserByCustom(@Param(Constants.WRAPPER) Wrapper<Userinfo> wrapper);


//    //把用户的age更新
//    @Update("update user_info set age = #{age} ${ew.customSqlSegment} ")
    Integer updateAgeByIdWrapper(@Param("age") Integer age, @Param(Constants.WRAPPER) Wrapper<Userinfo> wrapper);

}
