package com.amadeus.springblogdemo.pojo.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginRequest {

    @NotBlank
    private String userName;

    @NotBlank
    private String password;
}
