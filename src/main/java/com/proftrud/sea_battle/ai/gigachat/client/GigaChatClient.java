package com.proftrud.sea_battle.ai.gigachat.client;


import com.proftrud.sea_battle.ai.gigachat.bean.GigaChatImageRequest;
import com.proftrud.sea_battle.ai.gigachat.bean.GigaChatImageResponse;
import com.proftrud.sea_battle.ai.gigachat.bean.GigaChatRequest;
import com.proftrud.sea_battle.ai.gigachat.bean.GigaChatResponse;
import com.proftrud.sea_battle.ai.config.FeignCoreConfig;
import com.proftrud.sea_battle.ai.gigachat.config.GigaChatAuthConfig;
import com.proftrud.sea_battle.ai.gigachat.config.GigaChatConfig;
import com.proftrud.sea_battle.ai.gigachat.config.GigaChatQueryConfig;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "gigaChatClient",
        url = "https://gigachat.devices.sberbank.ru/api/v1",
        configuration = {FeignCoreConfig.class, GigaChatQueryConfig.class}
)
public interface GigaChatClient {

    @PostMapping("/images/generations")
    GigaChatImageResponse generateImage(
            @RequestHeader(value = "Authorization", required = false) String bearerToken,
            @RequestBody GigaChatImageRequest request
    );

    @PostMapping("/chat/completions")
    GigaChatResponse sendMessage(
            @RequestHeader(value = "Authorization", required = false) String bearerToken,
            @RequestBody GigaChatRequest.Request request
    );
}