package com.learn.event_marketing.configs;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.learn.event_marketing.constants.Prompts.MARKETING_SYSTEM_PROMPT;
import static com.learn.event_marketing.constants.Prompts.PERSONALIZED_POST_SYSTEM_PROMPT;

@Configuration
public class ChatClientConfigs {

    @Bean("marketing-chat-client")
    public ChatClient openAiMarketingChatClient(OpenAiChatModel chatModel) {
        return ChatClient.builder(chatModel)
                .defaultSystem(MARKETING_SYSTEM_PROMPT)
                .build();
    }

    @Bean("personalized-media-post-chat-client")
    public ChatClient openAiPersonalizedPostChatClient(OpenAiChatModel chatModel) {
        return ChatClient.builder(chatModel)
                .defaultSystem(PERSONALIZED_POST_SYSTEM_PROMPT)
                .build();
    }
}
