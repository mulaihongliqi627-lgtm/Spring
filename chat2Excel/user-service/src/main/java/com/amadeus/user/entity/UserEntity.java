package com.amadeus.user.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
@TableName("user")
public class UserEntity {


    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    /**
     * 用户名
     */
    @TableField(value = "user_name")
    private String userName;


    /**
     * 邮箱
     */
    private String email;

    /**
     * hash密码
     */
    private String passwordHash;
}
