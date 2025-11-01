package com.proftrud.sea_battle.config;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class WebConfig {

    @Bean
    public Gson defineGson() {
        return new Gson();
    }
}
