package com.learn.search.configs;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static org.springframework.ai.model.SpringAIModels.OPENAI;

@Configuration
public class ChatClientConfiguration {

    @Bean(name = "open-ai-client")
    @Profile(OPENAI)
    public ChatClient openAiChatClient(OpenAiChatModel chatModel) {
        return ChatClient.builder(chatModel).build();
    }
}
