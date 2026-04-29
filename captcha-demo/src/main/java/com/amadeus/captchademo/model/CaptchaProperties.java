package com.amadeus.captchademo.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "captcha")
@Data
public class CaptchaProperties {
    private int width;
    private int height;
    private SessionConfig session;
    
    @Data
    public static class SessionConfig {
        private String code;
        private String date;
    }
}
