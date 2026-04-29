package com.amadeus.mybatisdemo.mapper;

import com.amadeus.mybatisdemo.model.UserInfo;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface UserInfoMapperXML {

    List<UserInfo> getList();

    List<UserInfo> queryAllUser();

    UserInfo queryById(Integer id);

    Integer insert(UserInfo userInfo);

    Integer delete(String username);

    List<UserInfo> selectByAge(Integer id);

    Integer updateUsernameById(UserInfo userInfo);

    UserInfo queryByUsername(String username);

    List<UserInfo> queryAllSort(String sort);

    List<UserInfo> queryAllUserByLike(String key);

    Integer insertUserInfoByCondition(UserInfo userInfo);

}
