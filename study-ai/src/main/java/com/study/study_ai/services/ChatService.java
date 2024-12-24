package com.study.study_ai.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    @Autowired
    private ChatClient chatClient;

    public String generate(String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .chatResponse()
                .getResult()
                .getOutput()
                .getContent();
    }
}
