package com.amadeus.springbootdemo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/calc")
public class CalController {

    @RequestMapping("/sum")
    public String sum(Integer num1, Integer num2) { // 接收求和参数 [cite: 715]
        Integer sum = num1 + num2;
        // 返回 <h1> 标签包裹的结果，浏览器会自动解析
        return "<h1>计算机计算结果: " + sum + "</h1>";
    }
}
