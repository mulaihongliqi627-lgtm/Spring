package com.amadeus.lotterysystem.dao.mapper;


import com.amadeus.lotterysystem.dao.dataobject.Encrypt;
import com.amadeus.lotterysystem.dao.dataobject.UserDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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

}
