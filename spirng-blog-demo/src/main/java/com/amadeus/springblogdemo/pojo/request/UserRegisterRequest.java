package com.amadeus.springblogdemo.pojo.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRegisterRequest {
    @NotBlank(message = "用户名不能为null")
    private String userName;

    @NotBlank(message = "密码不能为null")
    private String password;
}
