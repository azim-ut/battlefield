package com.proftrud.sea_battle.ai.openai;

import com.google.gson.Gson;
import com.proftrud.sea_battle.ai.ChatService;
import com.proftrud.sea_battle.ai.openai.bean.OpenAiResponse;
import com.proftrud.sea_battle.ai.openai.bean.OpenAiResponseRequest;
import com.proftrud.sea_battle.ai.openai.client.OpenAiClient;
import com.proftrud.sea_battle.ai.openai.config.OpenAiConfig;
import com.proftrud.sea_battle.game.GameTable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service(value = "OpenAiChatService")
@RequiredArgsConstructor
public class OpenAiChatService implements ChatService {

    private final OpenAiClient openAiClient;
    private final OpenAiConfig openAiConfig;
    private final Gson gson;

    public String initGame(GameTable gameTable, String message) {

        log.info("Me: {}", message);
        OpenAiResponse responseBody = openAiClient.createResponse(openAiConfig.getApiKey(), new OpenAiResponseRequest(
                "gpt-4-turbo",
                message,
                null
        ));
        String responseId = responseBody.getId();
        gameTable.setId(responseId);
        String responseText = responseBody.getOutput().getFirst().getContent().getFirst().getText();
        log.info("AI: {}", responseText);
        return responseText;
    }

    public String makeTurn(GameTable gameTable, String message) {
        log.info("Me: {}", message);
        OpenAiResponse responseBody = openAiClient.createResponse(openAiConfig.getApiKey(), new OpenAiResponseRequest(
                "gpt-4-turbo",
                message,
                gameTable.getId()
        ));
        String responseId = responseBody.getId();
        gameTable.setId(responseId);
        String responseText = responseBody.getOutput().getFirst().getContent().getFirst().getText();
        log.info("AI: {}", responseText);
        return responseText;
    }
}