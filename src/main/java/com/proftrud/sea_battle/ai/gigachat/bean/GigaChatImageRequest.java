package com.proftrud.sea_battle.ai.gigachat.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class GigaChatImageRequest {
    private String prompt;
    private int numImages;
    private String size;

}


