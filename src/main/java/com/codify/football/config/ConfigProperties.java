package com.codify.football.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class ConfigProperties {
    @Value("${api.football.key}")
    private String apiKey;

    @Value("${api.football.url}")
    private String apiUrl;

    @Value("${restTemplate.server.timeout}")
    private Integer serverTimeOut;
}
