package com.amadeus.lotterysystem.dao.mapper;


import com.amadeus.lotterysystem.dao.dataobject.Encrypt;
import com.amadeus.lotterysystem.dao.dataobject.UserDO;
import com.amadeus.lotterysystem.dao.handler.EncryptTypeHandler;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sun.source.doctree.SeeTree;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<UserDO> {

    @Select("""
        SELECT COUNT(*)
        FROM user
        WHERE phone_number = #{phone,typeHandler=com.amadeus.lotterysystem.dao.handler.EncryptTypeHandler}
    """)
    long countByPhoneNumber(@Param("phone") Encrypt phoneNumber);


    @Select("""
        SELECT COUNT(*)
        FROM user
        WHERE email = #{email}
    """)
    long countByEmailLong(@Param("email") String email);

    @Select("""
        SELECT id, gmt_create, gmt_modified, user_name, email, phone_number, password, identity
        FROM user
        WHERE phone_number = #{phone,typeHandler=com.amadeus.lotterysystem.dao.handler.EncryptTypeHandler}
        LIMIT 1
    """)
    @Results(id = "UserDOMap", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "gmt_create", property = "gmtCreate"),
            @Result(column = "gmt_modified", property = "gmtModified"),
            @Result(column = "user_name", property = "userName"),
            @Result(column = "email", property = "email"),
            @Result(column = "phone_number", property = "phoneNumber", typeHandler = EncryptTypeHandler.class),
            @Result(column = "password", property = "password"),
            @Result(column = "identity", property = "identity")
    })
    UserDO selectByPhoneNumber(@Param("phone") Encrypt phoneNumber);



    @Select("""
        SELECT id, gmt_create, gmt_modified, user_name, email, phone_number, password, identity
        FROM user
        WHERE email = #{email}
        LIMIT 1
    """)
    @Results(value = {
            @Result(column = "id", property = "id"),
            @Result(column = "gmt_create", property = "gmtCreate"),
            @Result(column = "gmt_modified", property = "gmtModified"),
            @Result(column = "user_name", property = "userName"),
            @Result(column = "email", property = "email"),
            @Result(column = "phone_number", property = "phoneNumber", typeHandler = EncryptTypeHandler.class),
            @Result(column = "password", property = "password"),
            @Result(column = "identity", property = "identity")
    })
    UserDO selectByEmail(@Param("email") String email);



    @Select("<script>" +
            " select id, gmt_create, gmt_modified, user_name, email, phone_number, password, identity from user" +
            " <if test=\"identity!=null\">" +
            "    where identity = #{identity}" +
            " </if>" +
            " order by id desc" +
            " </script>")
    @ResultMap("UserDOMap")
    List<UserDO> selectUserListByIdentity(@Param("identity")String identity);


    @Select("<script>" +
            "SELECT id FROM user WHERE id IN " +
            "<foreach collection='userIds' item='id' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    List<Long> selectByIds(@Param("userIds") List<Long> userIds);
}
