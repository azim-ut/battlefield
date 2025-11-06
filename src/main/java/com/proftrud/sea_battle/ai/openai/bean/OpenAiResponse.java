package com.proftrud.sea_battle.ai.openai.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpenAiResponse {
    private String id;
    private List<Output> output;

    @Data
    public static class Output {
        private List<Content> content;
    }

    @Data
    public static class Content {
        private String type;
        private String text;
    }
}
