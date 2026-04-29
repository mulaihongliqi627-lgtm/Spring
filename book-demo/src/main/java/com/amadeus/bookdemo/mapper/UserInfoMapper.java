package com.amadeus.bookdemo.mapper;


import com.amadeus.bookdemo.model.UserInfo;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserInfoMapper {

    @Select("select * from user_info where deleteFlag = #{deleteFlag} and userName = #{userName}")
    UserInfo queryUserInfoByName(String name);
}
