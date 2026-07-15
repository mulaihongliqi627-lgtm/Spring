package com.amadeus.lotterysystem.controller;

import com.amadeus.lotterysystem.controller.param.UserRegisterParam;
import com.amadeus.lotterysystem.service.UserService;
import com.amadeus.lotterysystem.service.dto.UserRegisterDTO;
import com.amadeus.lotterysystem.service.enums.UserIdentityEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void publicRegistrationForcesNormalIdentity() {
        UserRegisterParam param = registerParam(UserIdentityEnum.ADMIN);
        when(userService.register(any(UserRegisterParam.class))).thenReturn(registerResult());

        userController.userRegister(param);

        ArgumentCaptor<UserRegisterParam> captor = ArgumentCaptor.forClass(UserRegisterParam.class);
        verify(userService).register(captor.capture());
        assertEquals(UserIdentityEnum.NORMAL.name(), captor.getValue().getIdentity());
    }

    @Test
    void administratorRegistrationPreservesRequestedIdentity() {
        UserRegisterParam param = registerParam(UserIdentityEnum.ADMIN);
        when(userService.register(any(UserRegisterParam.class))).thenReturn(registerResult());

        userController.userRegisterByAdmin(param);

        ArgumentCaptor<UserRegisterParam> captor = ArgumentCaptor.forClass(UserRegisterParam.class);
        verify(userService).register(captor.capture());
        assertEquals(UserIdentityEnum.ADMIN.name(), captor.getValue().getIdentity());
    }

    private UserRegisterParam registerParam(UserIdentityEnum identity) {
        UserRegisterParam param = new UserRegisterParam();
        param.setName("测试用户");
        param.setMail("user@example.com");
        param.setPhoneNumber("13800138000");
        param.setPassword("abc123");
        param.setIdentity(identity.name());
        return param;
    }

    private UserRegisterDTO registerResult() {
        UserRegisterDTO result = new UserRegisterDTO();
        result.setUserId(1L);
        return result;
    }
}
