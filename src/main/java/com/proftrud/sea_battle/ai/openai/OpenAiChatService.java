package com.proftrud.sea_battle.ai.openai;

import com.google.gson.Gson;
import com.proftrud.sea_battle.ai.ChatService;
import com.proftrud.sea_battle.ai.bean.AiHistoryResponse;
import com.proftrud.sea_battle.ai.openai.bean.OpenAi4Request;
import com.proftrud.sea_battle.ai.openai.client.OpenAiClient;
import com.proftrud.sea_battle.ai.openai.config.OpenAiConfig;
import com.proftrud.sea_battle.game.GameTable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service(value = "OpenAiChatService")
@RequiredArgsConstructor
public class OpenAiChatService implements ChatService {

    private final OpenAiClient openAiClient;
    private final OpenAiConfig openAiConfig;
    private final Gson gson;

    public AiHistoryResponse sendMessage(List<String> messages) {

        var request = new OpenAi4Request.Request(
                "gpt-4-turbo",
                new OpenAi4Request.ResponseFormat("json_object"),
                messages
                        .stream()
                        .map(m -> new OpenAi4Request.Message("user", m))
                        .toList()
        );
        log.info("Me: {}", messages);
        var responseBody = openAiClient.sendMessage(openAiConfig.getApiKey(), request);
        var content = responseBody
                        .choices()
                        .getFirst()
                        .message()
                        .content();
        log.info("AI: {}", content);
        return gson.fromJson(content, AiHistoryResponse.class);
    }
}