package com.amadeus.lotterysystem.common.interceptor;

import com.amadeus.lotterysystem.common.utils.JWTUtil;
import com.amadeus.lotterysystem.service.enums.UserIdentityEnum;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AdminInterceptorTest {

    private final AdminInterceptor adminInterceptor = new AdminInterceptor();

    @Test
    void loginInterceptorExposesVerifiedClaimsToAuthorizationInterceptors() throws Exception {
        LoginInterceptor loginInterceptor = new LoginInterceptor();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.addHeader("user_token", tokenFor(UserIdentityEnum.ADMIN));

        assertTrue(loginInterceptor.preHandle(request, response, new Object()));
        assertNotNull(request.getAttribute(LoginInterceptor.LOGIN_CLAIMS_ATTRIBUTE));
    }

    @Test
    void administratorCanAccessAdministratorEndpoints() {
        MockHttpServletRequest request = requestWithIdentity(UserIdentityEnum.ADMIN);
        MockHttpServletResponse response = new MockHttpServletResponse();

        assertTrue(adminInterceptor.preHandle(request, response, new Object()));
    }

    @Test
    void normalUserCannotAccessAdministratorEndpoints() {
        MockHttpServletRequest request = requestWithIdentity(UserIdentityEnum.NORMAL);
        MockHttpServletResponse response = new MockHttpServletResponse();

        assertFalse(adminInterceptor.preHandle(request, response, new Object()));
        assertEquals(403, response.getStatus());
    }

    @Test
    void missingVerifiedClaimsIsUnauthorized() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        assertFalse(adminInterceptor.preHandle(request, response, new Object()));
        assertEquals(401, response.getStatus());
    }

    private MockHttpServletRequest requestWithIdentity(UserIdentityEnum identity) {
        MockHttpServletRequest request = new MockHttpServletRequest();
        Claims claims = JWTUtil.parseJWT(tokenFor(identity));
        request.setAttribute(LoginInterceptor.LOGIN_CLAIMS_ATTRIBUTE, claims);
        return request;
    }

    private String tokenFor(UserIdentityEnum identity) {
        return JWTUtil.genJwt(Map.of("identity", identity.name()));
    }
}
