package com.amadeus.captchademo.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.lang.Console;
import com.amadeus.captchademo.model.CaptchaProperties;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/captcha")
public class CaptchaController {

    private final static long VALID_MILLIS_TIME = 2 * 60 * 1000; //2分钟
    @Autowired
    private CaptchaProperties captchaProperties;


    @GetMapping("/getCaptcha")
    public void getCaptcha(HttpServletResponse response, HttpSession session){
        log.info("后端收到请求");
        //定义图形验证码的长和宽
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(captchaProperties.getWidth(),
                captchaProperties.getHeight());

        String code = lineCaptcha.getCode();
        System.out.println(code);
        //更适合使用常量
        session.setAttribute(captchaProperties.getSession().getCode(), code);
        session.setAttribute(captchaProperties.getSession().getDate(), System.currentTimeMillis());

        //写出到浏览器
        response.setContentType("image/jpeg");
        response.setHeader("Pragma","No-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires", 0);
        try {
            lineCaptcha.write(response.getOutputStream());
            response.getOutputStream().close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/check")
    public boolean verify(String captcha, HttpSession session){
        /**
         * 1. 参数校验
         * 2. 从session中获取code以及日期, 判断验证码是否正确, 以及是否过期
         * 3. 返回
         */
        if (!StringUtils.hasText(captcha)){
            return false;
        }
        String savedCaptchaCode = (String)session.getAttribute(captchaProperties.getSession().getCode());
        long time = (Long)session.getAttribute(captchaProperties.getSession().getDate()); //验证码生成时间
        if (captcha.equalsIgnoreCase(savedCaptchaCode) && System.currentTimeMillis()-time < VALID_MILLIS_TIME){
            return true;
        }
        return false;
    }
}
