package com.proftrud.sea_battle.ai.gigachat;

import com.google.gson.Gson;
import com.proftrud.sea_battle.ai.ChatService;
import com.proftrud.sea_battle.ai.bean.AiAnswer;
import com.proftrud.sea_battle.ai.gigachat.bean.GigaChatRequest;
import com.proftrud.sea_battle.ai.gigachat.bean.GigaChatResponse;
import com.proftrud.sea_battle.ai.gigachat.client.GigaChatClient;
import com.proftrud.sea_battle.ai.gigachat.config.GigaChatConfig;
import com.proftrud.sea_battle.game.GameTable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service(value = "GigaChatService")
@RequiredArgsConstructor
public class GigaChatService implements ChatService {

    private final GigaChatClient gigaChatClient;
    private final GigaChatAuthProvider gigaChatAuthProvider;
    private final Gson gson;
    private final GigaChatConfig gigaChatConfig;

    @Override
    public String initGame(GameTable gameTable, String message) {

        log.info("initGame Me: {}", message);
        var token = gigaChatAuthProvider.getBearerToken();
        GigaChatResponse responseBody = gigaChatClient.sendMessage(token, new GigaChatRequest.Request(
                "gpt-4-turbo",
                new GigaChatRequest.ResponseFormat("user"),
                List.of(new GigaChatRequest.Message("user", message))
        ));
        log.info("initGame AI answer: {}", responseBody);
        return responseBody.toString();
    }

    @Override
    public AiAnswer makeTurn(GameTable gameTable, String message) {

        log.info("makeTurn: {}", message);
        var token = gigaChatAuthProvider.getBearerToken();
        GigaChatResponse responseBody = gigaChatClient.sendMessage(token, new GigaChatRequest.Request(
                "gpt-4-turbo",
                new GigaChatRequest.ResponseFormat("user"),
                List.of(new GigaChatRequest.Message("user", message))
        ));
        log.info("makeTurn AI answer: {}", responseBody);
        return new AiAnswer("", responseBody.toString());
    }
}