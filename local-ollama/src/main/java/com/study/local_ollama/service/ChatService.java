package com.study.local_ollama.service;

import com.study.local_ollama.model.GenerationRequest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private ChatClient chatClient;

    public ChatService(ChatClient.Builder chatClientBuilder) {
        chatClientBuilder.defaultSystem("You are a software developer and always explain the concepts very deeply but in simple bulleted manner.");
        this.chatClient = chatClientBuilder.build();
    }

    public String generate(GenerationRequest request) {
        return chatClient.prompt(request.input())
                .call()
                .chatResponse()
                .getResult()
                .getOutput()
                .getContent();
    }
}
