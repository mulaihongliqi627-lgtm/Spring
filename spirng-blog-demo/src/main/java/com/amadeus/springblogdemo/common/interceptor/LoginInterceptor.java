package com.amadeus.springblogdemo.common.interceptor;

import com.amadeus.springblogdemo.common.exception.BlogException;
import com.amadeus.springblogdemo.common.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("Authorization");
        if (!StringUtils.hasText(token)) {
            log.warn("request path: {}, missing token", request.getRequestURI());
            throw new BlogException("请先登录");
        }

        Claims claims = JwtUtils.parseJwt(token);
        if (claims == null) {
            log.warn("request path: {}, invalid token", request.getRequestURI());
            throw new BlogException("登录已过期，请重新登录");
        }

        request.setAttribute("userId", claims.get("userId", Integer.class));
        request.setAttribute("userName", claims.get("userName", String.class));
        return true;
    }
}
