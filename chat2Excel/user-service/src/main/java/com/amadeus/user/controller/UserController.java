package com.amadeus.user.controller;

import com.amadeus.user.annotation.LogOperation;
import com.amadeus.user.dto.request.SendCodeRequest;
import com.amadeus.user.dto.response.SendCodeResponse;
import com.amadeus.user.service.UserService;
import com.amadeus.user.service.VerificationCodeService;
import com.amadeus.user.util.Result;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private VerificationCodeService verificationCodeService;

    @RequestMapping("/verification-code")
    @LogOperation("发送验证码")
    public Result<SendCodeResponse> sendVerificationCode(SendCodeRequest request){
        int expireSeconds = verificationCodeService.sendCode(request.getEmail());
        //邮箱脱敏
        String sendTo = maskEmail(request.getEmail());
        SendCodeResponse response = SendCodeResponse.builder()
                .expireTime(expireSeconds)
                .sendTo(sendTo)
                .build();
        return Result.success("发送成功",response);
    }

    private String maskEmail(@NotBlank String email) {
        String [] parts = email.split("@",2);
        String name = parts[0];
        String domain = parts[1];
        if(name.length() < 2){
            return name.charAt(0) + "****@" + domain;
        }else{
            return name.substring(0,2) + "****@" + domain;
        }
    }
}
