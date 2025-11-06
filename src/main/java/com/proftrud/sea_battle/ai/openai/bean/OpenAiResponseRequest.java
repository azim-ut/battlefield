package com.proftrud.sea_battle.ai.openai.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpenAiResponseRequest {
    private String model;
    private String input;

    @JsonProperty("previous_response_id")
    private String previousResponseId;
}
