package com.njuse.uctaserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = {"classpath:microprogram.properties"},ignoreResourceNotFound = true)
public class MicroProgram {
    @Value("${appID}")
    private String appID;

    @Value("${appSecret}")
    private String appSecret;

    public String getAppID() {
        return appID;
    }

    public String getAppSecret() {
        return appSecret;
    }
}
