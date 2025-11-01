package com.proftrud.sea_battle;

import com.proftrud.sea_battle.ai.gigachat.config.GigaChatConfig;
import com.proftrud.sea_battle.ai.openai.config.OpenAiConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableConfigurationProperties({
        GigaChatConfig.class,
        OpenAiConfig.class
})
@SpringBootApplication
public class SeaBattleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeaBattleApplication.class, args);
	}

}
