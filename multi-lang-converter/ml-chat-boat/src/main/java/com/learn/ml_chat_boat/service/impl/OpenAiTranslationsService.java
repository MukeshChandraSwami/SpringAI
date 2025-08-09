package com.learn.ml_chat_boat.service.impl;

import com.learn.ml_chat_boat.models.Translations;
import com.learn.ml_chat_boat.request.TranslationsRequest;
import com.learn.ml_chat_boat.service.TranslationsService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static org.springframework.ai.model.SpringAIModels.OPENAI;

@Service
@Profile(OPENAI)
public class OpenAiTranslationsService implements TranslationsService {

    private final ChatClient chatClient;

    public OpenAiTranslationsService(@Qualifier("open-ai-client") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public Map<String, List<String>> translate(TranslationsRequest request) {
        Translations translations = chatClient.prompt()
                .user(spec -> spec.text(TRANSLATION_TEMPLATE)
                        .param("userText", request.getInputString())
                        .param("languages", request.getLangs())
                )
                .call()
                .entity(new ParameterizedTypeReference<>() {
                });
        return translations.getTranslations();
    }
}
