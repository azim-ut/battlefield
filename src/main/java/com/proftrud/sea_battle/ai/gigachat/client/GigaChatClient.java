package com.proftrud.sea_battle.ai.gigachat.client;


import com.proftrud.sea_battle.ai.gigachat.bean.GigaChatRequest;
import com.proftrud.sea_battle.ai.gigachat.bean.GigaChatResponse;
import com.proftrud.sea_battle.ai.config.FeignCoreConfig;
import com.proftrud.sea_battle.ai.gigachat.config.GigaChatAuthConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "gigaChatClient",
        url = "https://gigachat.devices.sberbank.ru/api/v1",
        configuration = {FeignCoreConfig.class, GigaChatAuthConfig.class}
)
public interface GigaChatClient {

    @PostMapping("/chat/completions")
    GigaChatResponse sendMessage(
            @RequestHeader(value = "Authorization", required = false) String bearerToken,
            @RequestBody GigaChatRequest.Request request
    );
}