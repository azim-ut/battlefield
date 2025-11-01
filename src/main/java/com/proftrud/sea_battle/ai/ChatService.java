package com.proftrud.sea_battle.ai;

import com.proftrud.sea_battle.ai.bean.AiHistoryResponse;

import java.util.List;

public interface ChatService {
    AiHistoryResponse sendMessage(List<String> message);
}