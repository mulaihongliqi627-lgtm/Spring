package com.amadeus.lotterysystem.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "sms")
public class SmsProperties {

    private String accessKeyId;

    private String accessKeySecret;

    private String signName;

    private String templateCode;

    private String regionId = "cn-hangzhou";

    private int codeExpireMinutes = 5;

    private int resendIntervalSeconds = 60;
}
