package com.proftrud.sea_battle.api.bean;

public record AiResponse(
        boolean active,
        boolean correct,
        String avatar,
        String message,
        String move
){};