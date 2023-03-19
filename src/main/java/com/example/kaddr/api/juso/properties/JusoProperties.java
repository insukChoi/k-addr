package com.example.kaddr.api.juso.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "api.juso")
public final class JusoProperties {
    private String uri;
    private String key;
}