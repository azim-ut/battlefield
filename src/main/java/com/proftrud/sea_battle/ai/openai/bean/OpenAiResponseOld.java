package com.proftrud.sea_battle.ai.openai.bean;

import java.util.List;

public class OpenAiResponseOld {

    public record Body(String id, String object, Long created, String model, List<Choice> choices, Usage usage){}

    public record Choice(int index, ChoiceMessage message){}

    public record ChoiceMessage(String role, String content, String finish_reason){}

    public record Usage(int prompt_tokens, int completion_tokens, int total_tokens){}

}
