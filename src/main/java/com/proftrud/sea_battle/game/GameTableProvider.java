package com.proftrud.sea_battle.game;


import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@AllArgsConstructor
public class GameTableProvider {

    private final Cache<String, GameTable> cache = Caffeine.newBuilder()
            .expireAfterAccess(25, TimeUnit.MINUTES)
            .maximumSize(10_000)
            .build();

    public void cleanUpCache(String key) {
        cache.invalidate(key);
    }

    public GameTable reset(String key) {
        this.cleanUpCache(key);
        return cache.get(key, k -> new GameTable());
    }

    public GameTable get(String key) {
        return cache.get(key, k -> new GameTable());
    }
}
