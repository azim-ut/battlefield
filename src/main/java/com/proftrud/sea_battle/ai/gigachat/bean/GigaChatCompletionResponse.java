package com.proftrud.sea_battle.ai.gigachat.bean;

import java.util.List;

public record GigaChatCompletionResponse(
        List<Choice> choices,
        long created,
        String model,
        String object
) {
    public record Choice(
            Message message,
            int index
    ) {}

    public record Message(
            String content,
            String role
    ) {}
}