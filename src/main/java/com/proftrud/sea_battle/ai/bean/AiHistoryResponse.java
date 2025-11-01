package com.proftrud.sea_battle.ai.bean;

import com.proftrud.sea_battle.game.GameTable;

import java.util.List;

public record AiHistoryResponse(String sessionId, String status, List<GameTable.HistoryRow> history) {
}
