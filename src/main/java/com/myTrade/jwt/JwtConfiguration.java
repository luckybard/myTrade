package com.myTrade.jwt;

import com.google.common.net.HttpHeaders;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@ConfigurationProperties(prefix = "application.jwt")
@Data
@Configuration
public class JwtConfiguration {
    private String secretKey;
    private String tokenPrefix;
    private String authTokenPrefixCookie;
    private Integer tokenExpirationAfterDays;

    public String getCookieHeader() {
        return HttpHeaders.COOKIE;
    }
}
