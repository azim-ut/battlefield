package com.proftrud.sea_battle.ai.gigachat.config;

import com.proftrud.sea_battle.ai.gigachat.GigaChatAuthProvider;
import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration(value = "gigaChatAuthConfig")
@RequiredArgsConstructor
public class GigaChatAuthConfig {

    @Bean(name = "gigaAuthInterceptor")
    public RequestInterceptor gigaAuthInterceptor(GigaChatAuthProvider gigaChatAuthService) {
        return requestTemplate -> {
            requestTemplate.header("Content-Type", "application/json");
            String token = gigaChatAuthService.getBearerToken();
            requestTemplate.header("Authorization", token);
        };
    }
}
