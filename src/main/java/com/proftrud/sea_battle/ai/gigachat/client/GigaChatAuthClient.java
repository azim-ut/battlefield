package com.proftrud.sea_battle.ai.gigachat.client;

import com.proftrud.sea_battle.ai.gigachat.config.GigaChatAuthConfig;
import com.proftrud.sea_battle.ai.gigachat.config.GigaChatSSLBypassConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(
        name = "gigaAuthClient",
        url = "https://ngw.devices.sberbank.ru:9443/api/v2",
        configuration = {GigaChatAuthConfig.class, GigaChatSSLBypassConfig.class}
)
public interface GigaChatAuthClient {

    @PostMapping(value = "/oauth", consumes = "application/x-www-form-urlencoded")
    Map<String, Object> getToken(
            @RequestHeader("Authorization") String basicAuth,
            @RequestHeader("RqUID") String rqUid,
            @RequestBody String body);
}
