package com.study.cmd_chat_assistant.services;

import com.study.cmd_chat_assistant.advisors.CustomPgChatMemoryAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.Scanner;
import java.util.UUID;

@Service
public class CmdChatService {

    private ChatClient chatClient;
    private CustomPgChatMemoryAdvisor customPgChatMemoryAdvisor;

    public CmdChatService(ChatClient.Builder chatClientBuilder,
                          CustomPgChatMemoryAdvisor customPgChatMemoryAdvisor) {
        this.chatClient = chatClientBuilder.build();
        this.customPgChatMemoryAdvisor = customPgChatMemoryAdvisor;
    }

    public void chat() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter conversation ID: ");
        UUID chatId = validateAndGetChatId(scanner.nextLine());

        while (true) {
            System.out.print("You: ");
            String message = scanner.nextLine();
            if (message.equals("exit")) {
                break;
            }
            customPgChatMemoryAdvisor.setConversationId(chatId);
            String response = chatClient
                    .prompt(message)
                    .advisors(customPgChatMemoryAdvisor)
                    .call().content();
            System.out.println("Bot: " + response);
        }
        scanner.close();
    }

    private UUID validateAndGetChatId(String chatId) {
        try {
            return UUID.fromString(chatId);
        } catch (IllegalArgumentException e) {
            return UUID.randomUUID();
        }
    }
}
