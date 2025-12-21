package com.proftrud.sea_battle.ai;

import com.proftrud.sea_battle.api.bean.AiResponse;
import com.proftrud.sea_battle.game.GameTable;

import java.nio.file.Path;

public interface ChatService {

    AiResponse initGame(GameTable gameTable, String message);
    AiResponse makeTurn(GameTable gameTable, String message);
    String RawAnswer(GameTable gameTable, String prompt);
    Path generateAvatar(String prompt);
}