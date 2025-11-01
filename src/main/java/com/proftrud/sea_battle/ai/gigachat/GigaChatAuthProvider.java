package com.proftrud.sea_battle.ai.gigachat;

import com.proftrud.sea_battle.ai.gigachat.client.GigaChatAuthClient;
import com.proftrud.sea_battle.ai.gigachat.config.GigaChatConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Service
@RequiredArgsConstructor
public class GigaChatAuthProvider {

    private final GigaChatAuthClient authClient;
    private final GigaChatConfig gigaChatConfig;


    private String accessToken;
    private Instant expiresAt;
    private final ReentrantLock lock = new ReentrantLock();

    public String getBearerToken() {
        lock.lock();
        try {
            if (accessToken == null || Instant.now().isAfter(expiresAt)) {
                refreshToken();
            }
            return "Bearer " + accessToken;
        } finally {
            lock.unlock();
        }
    }

    private void refreshToken() {
        String credentials = Base64.getEncoder()
                .encodeToString((gigaChatConfig.getClientId() + ":" + gigaChatConfig.getClientSecret()).getBytes());
        String basic = "Basic " + credentials;

        try {
            Map<String, Object> response = authClient.getToken(basic, "scope=GIGACHAT_API_PERS");
            accessToken = (String) response.get("access_token");
            int expiresIn = ((Number) response.getOrDefault("expires_in", 1800)).intValue();
            expiresAt = Instant.now().plusSeconds(expiresIn - 30);
            log.info("✅ Refreshed GigaChat token ({}s valid)", expiresIn);
        } catch (Exception e) {
            log.error("❌ Failed to refresh GigaChat token", e);
            throw new RuntimeException("GigaChat auth failed", e);
        }
    }
}
