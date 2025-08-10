package com.learn.ml_chat_boat.service;

import com.learn.ml_chat_boat.models.Translations;
import com.learn.ml_chat_boat.request.TranslationsRequest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public abstract class TranslationsService {

    private final ChatClient chatClient;

    public TranslationsService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public static final String TRANSLATION_TEMPLATE = """
            Translate {userText} to {languages}
            """;

    public Map<String, List<String>> translate(TranslationsRequest request) {
        return chatClient.prompt()
                .user(spec -> spec.text(TRANSLATION_TEMPLATE)
                        .param("userText", request.getInputString())
                        .param("languages", request.getLangs())
                )
                .call()
                .entity(Translations.class)
                .getTranslations();
    }
}
