package com.proftrud.sea_battle.ai.gigachat.bean;

import java.util.List;

public record GigaChatCompletionResponse(
        List<Choice> choices,
        long created,
        String model,
        String object
) {
    public record Choice(
            Delta delta,
            int index
    ) {}

    public record Delta(
            String content,
            String role
    ) {}
}