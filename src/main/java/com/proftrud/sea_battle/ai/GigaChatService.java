package com.proftrud.sea_battle.ai;

import com.proftrud.sea_battle.ai.bean.AiAnswer;
import com.proftrud.sea_battle.ai.gigachat.GigaChatAuthProvider;
import com.proftrud.sea_battle.ai.gigachat.bean.GigaChatCompletionResponse;
import com.proftrud.sea_battle.ai.gigachat.bean.GigaChatImageRequest;
import com.proftrud.sea_battle.ai.gigachat.bean.GigaChatImageResponse;
import com.proftrud.sea_battle.ai.gigachat.bean.GigaChatRequest;
import com.proftrud.sea_battle.ai.gigachat.bean.GigaChatResponse;
import com.proftrud.sea_battle.ai.gigachat.client.GigaChatClient;
import com.proftrud.sea_battle.game.GameTable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;

@Slf4j
@Service(value = "GigaChatService")
@RequiredArgsConstructor
public class GigaChatService implements ChatService {

    private final GigaChatClient gigaChatClient;
    private final GigaChatAuthProvider gigaChatAuthProvider;

    @Override
    public String initGame(GameTable gameTable, String message) {

        log.info("initGame Me: {}", message);
        var token = gigaChatAuthProvider.getBearerToken();
        GigaChatCompletionResponse responseBody = gigaChatClient.sendMessage(token, new GigaChatRequest.Request(
                "GigaChat-2",
                List.of(new GigaChatRequest.Message("user", message))
        ));
        log.info("initGame AI answer: {}", responseBody);
        return responseBody.toString();
    }

    @Override
    public AiAnswer makeTurn(GameTable gameTable, String message) {

        log.info("makeTurn: {}", message);
        var token = gigaChatAuthProvider.getBearerToken();
        GigaChatCompletionResponse responseBody = gigaChatClient.sendMessage(token, new GigaChatRequest.Request(
                "GigaChat",
                List.of(new GigaChatRequest.Message("user", message))
        ));
        log.info("makeTurn AI answer: {}", responseBody);
        return new AiAnswer("", responseBody.toString());
    }

    @Override
    public Path generateAvatar(String prompt) {
        log.info("buildImage: {}", prompt);
        var token = gigaChatAuthProvider.getBearerToken();
        GigaChatImageResponse responseBody = gigaChatClient.generateImage(token, new GigaChatImageRequest()
                        .setSize("150x150px")
                        .setNumImages(1)
                        .setPrompt(prompt)
        );


        log.info("buildImage AI answer: {}", responseBody);

        try {
            return saveGigaChatImage(responseBody, "../../apps/data/avatars/"+System.currentTimeMillis()+".png");
        } catch (IOException e) {
            log.error("Error while saving image", e);
        }
        return null;
    }

    private Path saveGigaChatImage(GigaChatImageResponse response, String filePath) throws IOException {
        if (response == null || response.getData() == null || response.getData().isEmpty()) {
            throw new IllegalArgumentException("Response does not contain image data");
        }

        // берем первое изображение (если их может быть несколько)
        String base64 = response.getData().get(0).getB64Json();

        if (base64 == null || base64.isEmpty()) {
            throw new IllegalArgumentException("Image base64 data is empty");
        }

        byte[] imageBytes = Base64.getDecoder().decode(base64);

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(imageBytes);
        }
        return Path.of(filePath);
    }
}