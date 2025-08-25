package com.learn.chat_assistant.controllers;

import com.learn.chat_assistant.responses.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.learn.chat_assistant.constants.RoutingConstants.USER_ID;

@RestController
@RequestMapping(USER_ID)
public class ChatController {

    @GetMapping
    public Response getAllChats(@PathVariable UUID userId) {
        return Response.builder()
                .success(true)
                .responseMsg("This endpoint will return all chats for the user.")
                .responseCode(200)
                .build();
    }
}
