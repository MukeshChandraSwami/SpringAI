package com.learn.chat_assistant.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final ChatClient chatClient;

    public ChatService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public void startNewConversation() {
        // Logic to start a new chat
    }

    public void getAllChats() {
        // Logic to retrieve all chats
    }

    public void getChatById(String chatId) {
        // Logic to retrieve a specific chat by ID
    }
}
