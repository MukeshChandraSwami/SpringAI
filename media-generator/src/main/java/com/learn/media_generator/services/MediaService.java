package com.learn.media_generator.services;

import com.learn.media_generator.requests.MediaRequest;
import com.learn.media_generator.response.Response;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class MediaService {

    private final ChatClient chatClient;

    public MediaService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public Response generateMedia(MediaRequest request) {
        // Here we will communicate with the ChatClient to generate media content
        // Them store it to a specific location or database
        return Response.builder()
                .success(true)
                .responseCode(200)
                .responseMsg("Media generated successfully")
                .build();
    }
}
