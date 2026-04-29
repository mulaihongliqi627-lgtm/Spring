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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("login")
    public boolean login(String name, String password, HttpSession session){
//        1.打印日志
        log.info("用户登录:" +  name);
//        2.参数校验
        if(!StringUtils.hasText(name)
                || !StringUtils.hasText(password)){
            log.warn("账号或密码不能为空!");
            return false;
        }

        //3.调用service校验参数是否正确
        return userService.checkPassword(name,password,session);
    }

    @RequestMapping("getListByPage")
     public Result<PageResponse<BookInfo>> getListByPage(PageRequest pageRequest, HttpSession session){
        UserInfo userInfo = (UserInfo) session.getAttribute(Constants.SESSION_USER_KEY);
        if(userInfo == null || userInfo.getId() <= 0){
            log.info("用户未登录");
            return Result.unlogin();
        }
        log.info("查询图书列表,pageRequest:{}", pageRequest);
        PageResponse<BookInfo> response = BookService.getListByPage(pageRequest);
        return Result.success(response);

    }


}
