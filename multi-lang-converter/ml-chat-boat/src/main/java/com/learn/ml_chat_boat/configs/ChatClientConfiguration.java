package com.learn.ml_chat_boat.configs;

import org.springframework.ai.anthropic.AnthropicChatModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static com.learn.ml_chat_boat.constants.SystemPrompts.TRANSLATION_PROMPT;
import static org.springframework.ai.model.SpringAIModels.ANTHROPIC;
import static org.springframework.ai.model.SpringAIModels.OPENAI;

@Configuration
public class ChatClientConfiguration {

    @Bean(name = "open-ai-client")
    @Profile(OPENAI)
    public ChatClient openAiChatClient(OpenAiChatModel chatModel) {
        return ChatClient.builder(chatModel)
                .defaultSystem(TRANSLATION_PROMPT)
                .build();
    }

    @Bean(name = "anthropic-ai-client")
    @Profile(ANTHROPIC)
    public ChatClient anthropicAiChatClient(AnthropicChatModel chatModel) {
        return ChatClient.builder(chatModel)
                .defaultSystem(TRANSLATION_PROMPT)
                .build();
    }
}
