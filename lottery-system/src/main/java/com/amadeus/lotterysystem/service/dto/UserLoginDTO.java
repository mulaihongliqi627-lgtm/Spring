package com.amadeus.lotterysystem.service.dto;

import lombok.Data;

import java.io.Serializable;


@Data
public class UserLoginDTO implements Serializable {

    private String token;
    private String identity;
}
