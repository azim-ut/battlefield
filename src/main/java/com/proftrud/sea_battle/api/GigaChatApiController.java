package com.proftrud.sea_battle.api;

import com.google.gson.Gson;
import com.proftrud.sea_battle.ai.GigaChatService;
import com.proftrud.sea_battle.ai.bean.AiAnswer;
import com.proftrud.sea_battle.api.bean.AiResponse;
import com.proftrud.sea_battle.api.bean.GameInitRequest;
import com.proftrud.sea_battle.game.BattleFieldBuilder;
import com.proftrud.sea_battle.game.GameTable;
import com.proftrud.sea_battle.game.GameTableProvider;
import com.proftrud.sea_battle.game.MatrixHelper;
import com.proftrud.sea_battle.game.constants.Command;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/sea-battle/giga")
@RequiredArgsConstructor
public class GigaChatApiController {

    private final GameTableProvider battleFieldProvider;
    private final GigaChatService gigaChatService;
    private final MatrixHelper matrixHelper;
    private final Gson gson;

    @GetMapping(path = "/check/{uuid}")
    public ResponseEntity<?> checkGateTable(@PathVariable("uuid") String uuid){
        return ResponseEntity.ok(battleFieldProvider.get(uuid));
    }

    @GetMapping(path = "/reset/{uuid}")
    public ResponseEntity<?> resetGateTable(@PathVariable("uuid") String uuid){
        var gameTable = battleFieldProvider.reset(uuid);
        return ResponseEntity.ok(gameTable);
    }

    @PostMapping(path = "init/{uuid}")
    public ResponseEntity<?> initGateTable(@PathVariable("uuid") String uuid, @RequestBody GameInitRequest prompt){
        var gameTable = battleFieldProvider.get(uuid);
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

        var answer = gigaChatService.initGame(gameTable,
                prompt + "\n" +
                gson.toJson(gameTable.getDescription("AI"))
        );

        gameTable
                .clearMessages()
                .getPlayers()
                .forEach(matrixHelper::printToConsole);

        return ResponseEntity.ok(answer);
    }

    @PostMapping(path = "/turn/{uuid}")
    public ResponseEntity<?> getGateTable(@RequestBody String action, @PathVariable("uuid") String uuid){
        var table = battleFieldProvider.get(uuid);
        var aiAnswer = gigaChatService.makeTurn(table, action);
        return ResponseEntity.ok(new AiResponse(aiAnswer.toString(), isCorrect(table, action, aiAnswer)));
    }

    private boolean isCorrect(GameTable table, String action, AiAnswer aiAnswer){
        var correct = new AtomicBoolean(true);
        var fieldOptional = table
                .getPlayers()
                .stream()
                .filter(f -> f.getName().equals("AI"))
                .findFirst();
        var answerData = aiAnswer.answer();
        if(action.length() <= 3){
            fieldOptional.ifPresent(field -> {
                if(Command.isHitOrKillOrWin(answerData)){
                    correct.set(field.getResult(answerData) == 1);
                }
                if(Command.isMissOrLoos(answerData)){
                    correct.set(field.getResult(answerData) == 0);
                }
            });
        }
        return correct.get();
    }

}
