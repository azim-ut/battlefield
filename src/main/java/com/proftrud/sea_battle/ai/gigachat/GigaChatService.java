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

    @Override
    public String initGame(GameTable gameTable, String message) {
        return "";
    }

    @Override
    public String makeTurn(GameTable gameTable, String message) {
        return "";
    }
}