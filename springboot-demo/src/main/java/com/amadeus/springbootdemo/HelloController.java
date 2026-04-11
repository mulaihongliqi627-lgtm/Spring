package com.amadeus.springbootdemo;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping("/v1")
    public String hello1() {
        return "Hello, Cheems,这是你的第一个SpringBoot项目~";
    }

    @RequestMapping("/v2")
    public Integer getNum1(){
        int a= 10/5;
        return 10;
    }

    @GetMapping("/v3")
    public String v3(){
        return "只能发送get请求";
    }

    @PostMapping("/v4")
    public String v4(){
        return "只能支持post请求";
    }

    @DeleteMapping("/v5")
    public String v5(){
        return "只支持delete请求";
    }

    @PutMapping("/v6")
    public String v6(){
        return "只支持put请求";
    }
}

