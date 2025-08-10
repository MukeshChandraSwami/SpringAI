package com.learn.ml_chat_boat.service.impl;

import com.learn.ml_chat_boat.request.TranslationsRequest;
import com.learn.ml_chat_boat.service.TranslationsService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static org.springframework.ai.model.SpringAIModels.OPENAI;

@Service
@Profile(OPENAI)
public class OpenAiTranslationsService extends TranslationsService {


    public OpenAiTranslationsService(@Qualifier("open-ai-client") ChatClient chatClient) {
        super(chatClient);
    }

    @Override
    public Map<String, List<String>> translate(TranslationsRequest request) {
        return super.translate(request);
    }
}
