package com.amadeus.springbootdemo.controller;

import com.amadeus.springbootdemo.model.Person;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hello")
public class UserController {
    @RequestMapping  (value = "/sayHi",method = RequestMethod.POST)
    public String sayHi(){
        return "hello ,Spring MVC";
    }


    @RequestMapping("/m4")
    public Object method_4(@RequestParam(value = "time", required = true) String createtime) {
        return "接收到参数createtime:" + createtime;
    }

    @RequestMapping("/m7")
    public Object method7(@RequestBody Person person) {
        return person.toString();
    }


    // 访问路径类似：/hello/m8/5/zhangsan
    @RequestMapping("/m8/{id}/{name}")
    public String method8(@PathVariable Integer id, @PathVariable("name") String userName) {
        return "解析参数id:" + id + ", name: " + userName;
    }

    @GetMapping("/set")
    public String setData(HttpServletResponse response, HttpSession session) {
        // 显式写入一个 Cookie
        Cookie cookie = new Cookie("user_tag", "AI-Explorer-99");
        cookie.setPath("/");
        cookie.setMaxAge(3600); // 有效期1小时
        response.addCookie(cookie);

        // 向 Session 中存入一个属性
        session.setAttribute("user_level", "Vip-Member");

        return "数据已写入！Cookie: user_tag=AI-Explorer-99, Session: user_level=Vip-Member";
    }


    //获取cookie - 获取名为user_tag的cookie
    @GetMapping("/getCookie")
    public String getData(@CookieValue("user_tag") String userTag) {

        return "user_tag:" + userTag;
    }

    //获取session
    @GetMapping("/getSession")
    public String getSessionData(@SessionAttribute("user_level") String userLevel) {
        return "user_level:" + userLevel;
    }



}
