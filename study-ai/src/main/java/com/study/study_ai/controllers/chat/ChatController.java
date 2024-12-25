package com.study.study_ai.controllers.chat;

import com.study.study_ai.controllers.chat.requests.Question;
import com.study.study_ai.controllers.chat.responses.Answer;
import com.study.study_ai.controllers.chat.responses.FeaturesWrapper;
import com.study.study_ai.services.ChatService;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping
    public String chat(@RequestBody Question question) {
        return chatService.generate(question);
    }

    @PostMapping(path = "/full-response")
    public ChatResponse fullResponse(@RequestBody Question question) {
        return chatService.fullResponse(question);
    }

    @PostMapping(path = "/any-question")
    public Answer askQuestion(@RequestBody Question question) {
        return chatService.askQuestion(question);
    }

    @PostMapping(path = "/structured-list")
    public List<String> structuredList(@RequestBody Question question) {
        return chatService.structuredList(question);
    }

    @PostMapping(path = "/structured-map")
    public Map<String, FeaturesWrapper> structuredMap(@RequestBody Question question) {
        return chatService.structuredMap(question);
    }
}
