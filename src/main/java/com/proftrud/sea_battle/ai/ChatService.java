package com.proftrud.sea_battle.ai;

import com.proftrud.sea_battle.ai.bean.AiAnswer;
import com.proftrud.sea_battle.game.GameTable;

import java.nio.file.Path;

public interface ChatService {

    String initGame(GameTable gameTable, String message);
    AiAnswer makeTurn(GameTable gameTable, String message);
    Path generateAvatar(String prompt);
}