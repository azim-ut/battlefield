package com.proftrud.sea_battle.ai.openai.client;

import com.proftrud.sea_battle.ai.openai.bean.OpenAi4Request;
import com.proftrud.sea_battle.ai.openai.bean.OpenAiResponse;
import com.proftrud.sea_battle.ai.openai.bean.OpenAiResponseOld;
import com.proftrud.sea_battle.ai.config.FeignCoreConfig;
import com.proftrud.sea_battle.ai.openai.bean.OpenAiResponseRequest;
import com.proftrud.sea_battle.ai.openai.config.OpenAiAuthConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "openAiClient",
        url = "https://api.openai.com/v1",
        configuration = {FeignCoreConfig.class, OpenAiAuthConfig.class}
)
public interface OpenAiClient {

    @PostMapping("/chat/completions")
    OpenAiResponseOld.Body sendMessage(
            @RequestHeader(value = "Authorization", required = false) String bearerToken,
            @RequestBody OpenAi4Request.Request request
    );


    @PostMapping(value = "/responses", consumes = "application/json")
    OpenAiResponse createResponse(
            @RequestHeader(value = "Authorization", required = false) String bearerToken,
            @RequestBody OpenAiResponseRequest request
    );
}