package com.amadeus.springiocdemo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    //属性注入
    @Autowired
    @Lazy
    private UserService userService;
    public void sayHello(){
        System.out.println("hello UserController!");
    }
}
