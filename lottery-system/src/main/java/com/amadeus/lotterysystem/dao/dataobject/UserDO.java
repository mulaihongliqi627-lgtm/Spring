package com.amadeus.lotterysystem.dao.dataobject;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "user", autoResultMap = true)
public class UserDO extends BaseDO {
    /**
     * 用户名
     */
    private String userName;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    @TableField(typeHandler = com.amadeus.lotterysystem.dao.handler.EncryptTypeHandler.class)
    private Encrypt phoneNumber;
    /**
     * 密码
     */
    private String password;
    /**
     * 身份信息
     */
    private String identity;
}
