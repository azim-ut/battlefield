package com.proftrud.sea_battle.ai.openai.config;

import feign.RequestInterceptor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration(value = "openAiAuthConfig")
@RequiredArgsConstructor
public class OpenAiAuthConfig {

//    private final OpenAiConfig openAiConfig;

    @Bean(name = "openAiAuthInterceptor")
    public RequestInterceptor openAiAuthInterceptor(OpenAiConfig openAiConfig) {
        return requestTemplate -> {
            requestTemplate.header("Content-Type", "application/json");
        };
    }
}
