package com.study.cmd_chat_assistant.models;

import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;

import java.util.Map;

public class ChatMessage implements Message {

    private String content;

    public ChatMessage(String content) {
        this.content = content;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.USER;
    }

    @Override
    public String getContent() {
        return this.content;
    }

    @Override
    public Map<String, Object> getMetadata() {
        return Map.of();
    }
}
