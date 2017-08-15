package com.richard.money.api.config.property;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("money-api")
public class MoneyApiProperty {

    @Getter
    private final Security security = new Security();

    @Getter
    @Setter
    private String allowedOrigin = "http://localhost:8000";

    @Data
    public static class Security {
        private boolean enableHttps;
    }

}
