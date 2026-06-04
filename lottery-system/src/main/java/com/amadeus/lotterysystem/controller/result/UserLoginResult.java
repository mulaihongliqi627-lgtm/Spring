package com.amadeus.lotterysystem.controller.result;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLoginResult {

    /**
     * JWT令牌
     */
    private String token;


    /**
     * 身份信息
     */
    private String identity;
}
