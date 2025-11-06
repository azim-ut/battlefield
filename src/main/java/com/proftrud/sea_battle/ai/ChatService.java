package com.proftrud.sea_battle.ai;

import com.proftrud.sea_battle.game.GameTable;

public interface ChatService {

    String initGame(GameTable gameTable, String message);
    String makeTurn(GameTable gameTable, String message);
}