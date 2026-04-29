package com.amadeus.mybatisdemo.mapper;


import com.amadeus.mybatisdemo.model.UserInfo;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserInfoMapper {
    @Select("select * from user_info")
    List<UserInfo> getList();


//    @Select("select id, username, `password`, age, gender, phone, delete_flag as deleteFlag, " + "create_time as createTime, update_time as updateTime from user_info")
//    List<UserInfo> queryAllUser();

    @Select("select id, username, `password`, age, gender, phone, delete_flag, create_time, update_time from user_info")
    List<UserInfo> queryAllUser();

    @Select("select * from user_info where id = #{id};")
    UserInfo queryById(@Param("id") Integer id);

    //把自增主键id拿到并赋值给UserInfo对象
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into user_info (username, `password`, age, gender, phone) values (#{username},#{password},#{age},#{gender},#{phone})")
    Integer insert(UserInfo userInfo);

    @Delete("delete from user_info where username = #{username}")
    Integer delete(String username);

    @Select("select * from user_info where age = #{age};")
    List<UserInfo> selectByAge(Integer age);



}
