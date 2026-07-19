package com.amadeus.user.mapper;


import com.amadeus.user.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {


    /**
     * 根据登录信息查询用户
     * @param key 用户名or邮箱
     * @return 用户信息
     */
    @Select( "select * from user where email = #{key} or username = #{key}")
    UserEntity findByLoginKey(@Param("key")String key);


    /**
     * 判断用户是否存在
     * @param username
     * @return
     */
    @Select( "select count(1) from user where username = #{username}")
    int isExistByUsername(@Param("username")String username);

}
