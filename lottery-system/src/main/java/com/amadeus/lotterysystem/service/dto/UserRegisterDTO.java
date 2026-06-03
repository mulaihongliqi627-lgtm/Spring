package com.amadeus.lotterysystem.service.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegisterDTO implements Serializable {

    /**
     * 用户id
     */
    private Long userId;

}