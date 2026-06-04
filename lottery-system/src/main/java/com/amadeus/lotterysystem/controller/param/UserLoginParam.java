package com.amadeus.lotterysystem.controller.param;

import lombok.Data;

import java.io.Serializable;


@Data
public class UserLoginParam implements Serializable {

    /**
     * 登录方式
     */
    private String mandatoryIdentity;
}
