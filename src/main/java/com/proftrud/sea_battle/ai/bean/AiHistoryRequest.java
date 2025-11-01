package com.proftrud.sea_battle.ai.bean;

import com.proftrud.sea_battle.game.GameTable;

import java.util.List;

public record AiHistoryRequest(String sessionId, List<GameTable.HistoryRow> historyRows) {
}
