package com.study.cmd_chat_assistant.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class CmdChatService {

    private ChatClient chatClient;

    public CmdChatService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder
                .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                .build();
    }

    public void chat() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("You: ");
            String message = scanner.nextLine();
            if (message.equals("exit")) {
                break;
            }
            String response = chatClient.prompt(message).call().content();
            System.out.println("Bot: " + response);
        }
        scanner.close();
    }
}
