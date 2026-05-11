package com.amadeus.bookdemo.controller;

import com.amadeus.bookdemo.constant.Constants;
import com.amadeus.bookdemo.enums.ResultCode;
import com.amadeus.bookdemo.model.*;
import com.amadeus.bookdemo.service.BookService;
import com.amadeus.bookdemo.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private  UserService userService;

    @RequestMapping(value = "login", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<String> login(@RequestParam(required = false) String name, 
                                @RequestParam(required = false) String password, 
                                HttpSession session){
        log.info("用户登录 - name: [{}], password: [{}]", name, password);
        
        if(!StringUtils.hasText(name)
                || !StringUtils.hasText(password)){
            log.warn("账号或密码不能为空! name=[{}], password=[{}]", name, password);
            return Result.fail("账号或密码不能为空");
        }

        try {
            boolean result = userService.checkPassword(name, password, session);
            if (result) {
                log.info("登录成功");
                return Result.success("登录成功");
            } else {
                log.info("登录失败 - 用户名或密码错误");
                return Result.fail("用户名或密码错误");
            }
        } catch (Exception e) {
            log.error("登录异常", e);
            return Result.fail("登录异常，请稍后重试");
        }
    }


}
