package com.proftrud.sea_battle.api;

import com.google.gson.Gson;
import com.proftrud.sea_battle.ai.bean.AiHistoryRequest;
import com.proftrud.sea_battle.ai.bean.AiHistoryResponse;
import com.proftrud.sea_battle.ai.gigachat.GigaChatService;
import com.proftrud.sea_battle.ai.openai.OpenAiChatService;
import com.proftrud.sea_battle.ai.openai.config.OpenAiConfig;
import com.proftrud.sea_battle.game.BattleFieldBuilder;
import com.proftrud.sea_battle.game.GameTable;
import com.proftrud.sea_battle.game.GameTableProvider;
import com.proftrud.sea_battle.game.MatrixHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/sea-battle")
@RequiredArgsConstructor
public class ApiController {

    private final GameTableProvider battleFieldProvider;
    private final GigaChatService gigaChatService;
    private final OpenAiConfig openAiConfig;
    private final MatrixHelper matrixHelper;
    private final OpenAiChatService openAiChatService;
    private final Gson gson;

    @GetMapping(path = "/init/{uuid}")
    public ResponseEntity<?> getGateTable(@PathVariable("uuid") String uuid){
        var gameTable = battleFieldProvider.reset(uuid);
        int size = 10;

        gameTable.setFieldSize(size)
                .setName(uuid)
                .getPlayers().add(
                        new BattleFieldBuilder()
                                .setName("Player")
                                .setHeight(size)
                                .setWidth(size)
                                .build()
                );
        gameTable.getPlayers().add(
                new BattleFieldBuilder()
                        .setName("AI")
                        .setHeight(size)
                        .setWidth(size)
                        .build()
        );
        gameTable.getMessages().add(openAiConfig.getPromptStart() + "\n");
        gameTable.getMessages().add(gson.toJson(gameTable.getDescription("AI")));

        openAiChatService.sendMessage(gameTable.getMessages());

        gameTable
                .clearMessages()
                .getPlayers()
                .forEach(matrixHelper::printToConsole);

        return ResponseEntity.ok(gameTable.getState());
    }

    @PostMapping(path = "/action/{uuid}")
    public ResponseEntity<?> getGateTable(@RequestBody String target, @PathVariable("uuid") String uuid){

        var gameTable = battleFieldProvider.get(uuid).addHistory("player", "ai", target, -1);

        var replyAi = this.sendHistory(uuid, gameTable);
        gameTable.applyHistory(replyAi.historyRows());

        gameTable.getPlayers().forEach(matrixHelper::printToConsole);

        return ResponseEntity.ok(gameTable.getState());
    }

    private AiHistoryResponse sendHistory(String uuid, GameTable gameTable) {
        var queryToAi = new AiHistoryRequest(uuid, gameTable.getHistory());
        gameTable.getMessages().add("History json: " + gson.toJson(queryToAi));
        var out = openAiChatService.sendMessage(gameTable.getMessages());
        gameTable.getMessages().clear();
        return out;
    }

}
