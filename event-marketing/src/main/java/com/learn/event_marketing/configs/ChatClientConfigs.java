package com.learn.event_marketing.configs;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.learn.event_marketing.constants.Prompts.MARKETING_SYSTEM_PROMPT;

@Configuration
public class ChatClientConfigs {

    @Bean
    public ChatClient openAiChatClient(OpenAiChatModel chatModel) {
        return ChatClient.builder(chatModel)
                .defaultSystem(MARKETING_SYSTEM_PROMPT)
                .build();
    }
}
