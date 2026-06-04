package com.amadeus.lotterysystem.service.dto;

import com.amadeus.lotterysystem.service.enums.UserIdentityEnum;
import lombok.Data;

/**
 * @author: yibo
 */
@Data
public class UserDTO {
    /**
     * 用户id
     */
    private Long userId;
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
    private String phoneNumber;
    /**
     * 身份信息
     */
    private UserIdentityEnum identity;

}
