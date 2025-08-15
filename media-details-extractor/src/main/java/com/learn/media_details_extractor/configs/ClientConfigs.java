package com.learn.media_details_extractor.configs;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.learn.media_details_extractor.constants.Prompts.MEDIA_DETAILS_EXTRACTOR;

@Configuration
public class ClientConfigs {

    @Bean(name = "media-details-extractor-openai-client")
    public ChatClient openAiMediaDetailsExtractorClient(OpenAiChatModel chatModel) {
        return ChatClient.builder(chatModel)
                .defaultSystem(MEDIA_DETAILS_EXTRACTOR)
                .build();
    }
}
