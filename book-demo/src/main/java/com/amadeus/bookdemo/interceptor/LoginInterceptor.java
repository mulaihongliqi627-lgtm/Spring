package com.amadeus.bookdemo.interceptor;

import com.amadeus.bookdemo.constant.Constants;
import com.amadeus.bookdemo.model.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import tools.jackson.databind.ObjectMapper;


@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("用户请求: {}", request.getRequestURI());

        response.setContentType("application/json;charset=utf-8");
        HttpSession session = request.getSession();

        //校验失败,用户未登录,请求拦截
        if(session == null || session.getAttribute(Constants.SESSION_USER_KEY) == null){
            log.warn("用户未登录!" + request.getRequestURI());
            Result result = Result.unlogin();
            response.setStatus(401);
            response.getOutputStream().write(objectMapper.writeValueAsBytes(result));
            return false;
        }
        return true;
    }

//    public boolean postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        return HandlerInterceptor.super.postHandle(request, response, handler hd, ex);
//    }

}
