package com.study.cmd_chat_assistant.services;

import com.study.cmd_chat_assistant.advisors.CustomPgChatMemoryAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.image.ImageOptions;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.stereotype.Service;

import java.util.Scanner;
import java.util.UUID;

@Service
public class CmdChatService {

    private ChatClient chatClient;
    private CustomPgChatMemoryAdvisor customPgChatMemoryAdvisor;
    private CassandraVectorService cassandraVectorService;
    private OpenAiImageModel imageModel;

    public CmdChatService(ChatClient.Builder chatClientBuilder,
                          CustomPgChatMemoryAdvisor customPgChatMemoryAdvisor,
                          CassandraVectorService cassandraVectorService,
                          OpenAiImageModel imageModel) {
        this.chatClient = chatClientBuilder.build();
        this.customPgChatMemoryAdvisor = customPgChatMemoryAdvisor;
        this.cassandraVectorService = cassandraVectorService;
        this.imageModel = imageModel;
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
                    .functions("hotelBookingService")
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

            System.out.print("Do you want to generate an image (Y/N):- ");
            if(scanner.nextLine().equalsIgnoreCase("Y")) {
                System.out.print("Enter query: ");
                String query = scanner.nextLine();
                String url = this.imageModel.call(new ImagePrompt(query,
                                OpenAiImageOptions.builder()
                                        .withHeight(1024)
                                        .withQuality("hd")
                                        .withWidth(1024)
                                        .withN(1)
                                        .withResponseFormat("url")
                                        .build()))
                        .getResult().getOutput().getUrl();
                System.out.println("Image URL: " + url);
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
