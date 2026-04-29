package com.amadeus.springbootdemo.controller;


import com.amadeus.springbootdemo.model.LoginRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userLogin")
public class LoginController {


    //1.登录接口
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request, HttpSession session){
        //模拟数据库数据校验
        if("cheems".equals(request.getUserName()) && "123456".equals(request.getPassWord())){
            //登录成功
            session.setAttribute("loginUser",request.getUserName());
            //设置Session最大存储时间
            session.setMaxInactiveInterval(3600);
            return String.format("登录成功,欢迎回来! %s",request.getUserName());
        }
        return "账号或密码不正确";
    }

    //2.获取当前登录状态
    @GetMapping("checkLogin")
    public String checkLogin(HttpSession session){
        //从session中检索Session_ID
        Object userName = session.getAttribute("loginUser");
        //如果为空
        if(userName == null){
            return "未登录或会话已过期";
        }else{
            return "当前登录用户: " + userName.toString();
        }
    }


}
