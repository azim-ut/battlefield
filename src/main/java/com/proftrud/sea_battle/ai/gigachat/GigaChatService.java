package com.proftrud.sea_battle.ai.gigachat;

import com.google.gson.Gson;
import com.proftrud.sea_battle.ai.ChatService;
import com.proftrud.sea_battle.ai.bean.AiHistoryResponse;
import com.proftrud.sea_battle.ai.gigachat.bean.GigaChatRequest;
import com.proftrud.sea_battle.ai.gigachat.client.GigaChatClient;
import com.proftrud.sea_battle.game.GameTable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "GigaChatService")
@RequiredArgsConstructor
public class GigaChatService implements ChatService {

    private final GigaChatClient gigaChatClient;
    private final GigaChatAuthProvider gigaChatAuthProvider;
    private final Gson gson;

    public AiHistoryResponse sendMessage(List<String> message) {

        var request = new GigaChatRequest.Request(
                "gpt-4o-mini",
                new GigaChatRequest.ResponseFormat("text"),
                message
                        .stream()
                        .map(m -> new GigaChatRequest.Message("user", m))
                        .toList()
        );
        var gigaChatResponse = gigaChatClient
                .sendMessage(gigaChatAuthProvider.getBearerToken(), request);
        var content = gigaChatResponse.toString();
        return gson.fromJson(content, AiHistoryResponse.class);
    }
}