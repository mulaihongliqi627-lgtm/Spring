package com.amadeus.lotterysystem.controller.param;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserPasswordLogin extends UserLoginParam{


    @NotBlank(message = "邮箱/手机号不能为空")
    private String loginName;


    @NotBlank(message = "密码不能为空")
    private String password;
}
