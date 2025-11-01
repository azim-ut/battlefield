package com.proftrud.sea_battle.ai.openai.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "open-ai")
public class OpenAiConfig {
    private String apiKey;
    private String promptStart;
    private String promptDone;
}
