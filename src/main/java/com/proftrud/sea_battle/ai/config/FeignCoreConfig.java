package com.proftrud.sea_battle.ai.config;

import feign.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FeignCoreConfig {

    @Bean
    public Logger.Level feignLoggerLevel() { return Logger.Level.FULL; }
}