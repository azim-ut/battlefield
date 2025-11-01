package com.proftrud.sea_battle.ai.gigachat.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "giga-chat")
public class GigaChatConfig {
    private String authKey;
    private String clientId;
    private String clientSecret;
    private String promptStart;
    private String promptDone;
}
