package com.amadeus.springiocdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserController userController;

    //构造器注入
    @Autowired
    public UserService(@Lazy UserController userController){
        this.userController = userController;
    }
    public void sayHello(){
        System.out.println("Hello UserService!");
    }
}
