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
    private CassandraVectorService cassandraVectorService;

    public CmdChatService(ChatClient.Builder chatClientBuilder,
                          CustomPgChatMemoryAdvisor customPgChatMemoryAdvisor,
                          CassandraVectorService cassandraVectorService) {
        this.chatClient = chatClientBuilder.build();
        this.customPgChatMemoryAdvisor = customPgChatMemoryAdvisor;
        this.cassandraVectorService = cassandraVectorService;
    }

    public void chat() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter conversation ID: ");
        UUID chatId = validateAndGetChatId(scanner.nextLine());

        // cassandraVectorService.loadDocumentsInStore();
        // cassandraVectorService.loadMobileDocumentsInStore();

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
            System.out.print("Do you want to request data from local vector (Y/N):- ");
            if(scanner.nextLine().equalsIgnoreCase("Y")) {
                System.out.print("Enter query: ");
                String query = scanner.nextLine();
                cassandraVectorService.search(query).forEach(document -> {
                    System.out.println("Documents: " + document);
                });
            }
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
