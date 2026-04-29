package com.amadeus.springbootdemo.controller;


import jakarta.servlet.http.HttpSession;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class LoginController1 {



//    校验密码是否正确
    @PostMapping("/login")
    public boolean loogin(String userName, String password, HttpSession session){
        //账号or密码任意为空,返回false
        if(!StringUtils.hasText(userName)
                || !StringUtils.hasText(password)){
            return false;
        }

        //TODO 尚且写死账号和密码
        if(!"cheems".equals(userName) || !"123456".equals(password) ){
            return false;
        }

//        把账号名存在session中
        session.setAttribute("userName",userName);
        return true;
    }

    @RequestMapping("/getLoginUser")
    public String getLoginUser(HttpSession session){

        String userName = (String)session.getAttribute("userName");
        if(StringUtils.hasText(userName)){//session中已经保存userName字段
            return userName;
        }
        return "";//没找到userName返回空字符串


    }

}
