package com.proftrud.sea_battle.ai.gigachat.bean;

import java.util.List;

public class GigaChatRequest {

    public record RequestImage(
            String model,
            String prompt,
            String size,
            String quality,
            Long n
    ){}

    public record Request(
            String model,
            ResponseFormat response_format,
            List<Message> messages
    ){}

    public record RequestText(
            String model,
            List<Message> messages
    ){}

    public record ResponseFormat(String type){}

    public record Message(String role, String content){}

}
