package com.proftrud.sea_battle.ai.gigachat.config;

import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration(value = "gigaChatQueryConfig")
@RequiredArgsConstructor
public class GigaChatQueryConfig {

    @Bean
    public Encoder feignEncoder() {
        return new GsonEncoder();
    }

    @Bean
    public Decoder feignDecoder() {
        return new GsonDecoder();
    }

}
