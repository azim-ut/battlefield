package com.proftrud.sea_battle.ai.openai;

import com.google.gson.Gson;
import com.proftrud.sea_battle.ai.ChatService;
import com.proftrud.sea_battle.ai.openai.bean.OpenAiResponse;
import com.proftrud.sea_battle.ai.openai.bean.OpenAiResponseRequest;
import com.proftrud.sea_battle.ai.openai.client.OpenAiClient;
import com.proftrud.sea_battle.ai.openai.config.OpenAiConfig;
import com.proftrud.sea_battle.api.bean.AiResponse;
import com.proftrud.sea_battle.game.GameTable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Slf4j
@Service(value = "OpenAiChatService")
@RequiredArgsConstructor
public class OpenAiChatService implements ChatService {

    private final OpenAiClient openAiClient;
    private final OpenAiConfig openAiConfig;
    private final Gson gson;

    public AiResponse initGame(GameTable gameTable, String message) {

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
        return new AiResponse(gameTable.isActive(), false, "", "", "");
    }

    public AiResponse makeTurn(GameTable gameTable, String message) {
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
        StringBuilder description = new StringBuilder();
        String answer = "";

        String[] rows = responseText.split("\\n");
        for(int i = 0; i< rows.length-2; i++){
            description.append(rows[i]);
        }
        answer = rows[rows.length-1];

        return new AiResponse(gameTable.isActive(), true, "", description.toString(), answer);
    }

    @Override
    public String RawAnswer(GameTable gameTable, String prompt) {
        return "";
    }


    @Override
    public Path generateAvatar(String prompt) {
        return null;
    }
}