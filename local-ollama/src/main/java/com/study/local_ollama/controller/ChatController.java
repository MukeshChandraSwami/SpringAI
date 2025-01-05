package com.study.local_ollama.controller;

import com.study.local_ollama.model.GenerationRequest;
import com.study.local_ollama.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/chat/v1")
@RestController
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/generate")
    public String generate(@RequestBody GenerationRequest request) {
        return chatService.generate(request);
    }
}
