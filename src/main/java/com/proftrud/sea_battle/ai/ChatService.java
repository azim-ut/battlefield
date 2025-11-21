package com.proftrud.sea_battle.ai;

import com.proftrud.sea_battle.ai.bean.AiAnswer;
import com.proftrud.sea_battle.game.GameTable;

public interface ChatService {

    String initGame(GameTable gameTable, String message);
    AiAnswer makeTurn(GameTable gameTable, String message);
}