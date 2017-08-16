package com.richard.money.api.config.property;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("money-api")
public class MoneyApiProperty {

    @Getter
    private final Security security = new Security();

    @Getter
    private final Environment environment = new Environment();

    @Data
    public static class Environment {
        private String allowedOrigin;
    }

    @Data
    public static class Security {
        private boolean enableHttps;
    }

}
