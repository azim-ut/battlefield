package com.proftrud.sea_battle.ai.openai.bean;

import java.util.List;

public class OpenAi4Request {

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
            List<Message> input
    ){}

    public record ResponseFormat(String type){}

    public record Message(String role, String content){}

}
