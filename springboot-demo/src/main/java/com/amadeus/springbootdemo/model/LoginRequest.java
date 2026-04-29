package com.amadeus.springbootdemo.model;

import lombok.Data;

@Data
public class LoginRequest {
    private String userName;
    private String passWord;
}
