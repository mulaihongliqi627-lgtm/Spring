package com.amadeus.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class SendCodeResponse {

    /**
     * 脱敏之后的邮箱
     */
    private String sendTo;

    /**
     * 验证码过期时间
     */
    private Integer expireTime;
}
