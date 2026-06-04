package com.amadeus.lotterysystem.common.interceptor;


import com.amadeus.lotterysystem.common.utils.JWTUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从header 中获取token
        String jwtToken = normalizeToken(request.getHeader("user_token"));
        log.info("获取路径: {}",request.getRequestURI());
        log.info("获取token: {}",jwtToken);
        //解析token并验证
        Claims claims = JWTUtil.parseJWT(jwtToken);
        if(null == claims){
            log.error("token解析失败");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        log.info("token解析成功");
        return true;
    }

    private String normalizeToken(String token) {
        if (token == null) {
            return null;
        }
        return token.trim().replaceAll("^\"+|\"+$", "");
    }
}
