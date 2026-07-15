package com.amadeus.lotterysystem.common.interceptor;

import com.amadeus.lotterysystem.service.enums.UserIdentityEnum;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Object claimsAttribute = request.getAttribute(LoginInterceptor.LOGIN_CLAIMS_ATTRIBUTE);
        if (!(claimsAttribute instanceof Claims claims)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        String identity = claims.get("identity", String.class);
        if (!UserIdentityEnum.ADMIN.name().equalsIgnoreCase(identity)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }
        return true;
    }
}
