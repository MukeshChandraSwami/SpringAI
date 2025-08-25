package com.learn.media_generator.configs;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.learn.media_generator.constants.Prompts.MEDIA_GENERATOR_SYSTEM_PROMPT;


@Configuration
public class ChatClientConfigs {

    @Bean
    public ChatClient openAiChatClient(OpenAiChatModel chatModel) {
        return ChatClient.builder(chatModel)
                .defaultSystem(MEDIA_GENERATOR_SYSTEM_PROMPT)
                .build();
    }
}
