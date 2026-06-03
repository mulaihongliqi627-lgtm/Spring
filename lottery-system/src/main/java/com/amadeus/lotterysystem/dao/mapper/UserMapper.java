package com.amadeus.lotterysystem.dao.mapper;


import com.amadeus.lotterysystem.dao.dataobject.Encrypt;
import com.amadeus.lotterysystem.dao.dataobject.UserDO;
import com.amadeus.lotterysystem.dao.handler.EncryptTypeHandler;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

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
        WHERE email = #{email,typeHandler=com.amadeus.lotterysystem.dao.handler.EncryptTypeHandler}
    """)
    long countByEmailLong(@Param("email") Encrypt email);

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

}
