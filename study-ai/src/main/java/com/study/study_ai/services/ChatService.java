package com.study.study_ai.services;

import com.study.study_ai.controllers.chat.requests.Question;
import com.study.study_ai.controllers.chat.responses.Answer;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private static final String GENERAL_QUESTION = """
            Explain with example {about} for the {question}?
            """;

    @Autowired
    private ChatClient chatClient;

    public String generate(Question question) {
        return chatClient.prompt()
                .user(question.getQuestion())
                .call()
                .chatResponse()
                .getResult()
                .getOutput()
                .getContent();
    }

    public ChatResponse fullResponse(Question question) {
        return chatClient.prompt()
                .user(question.getQuestion())
                .call()
                .chatResponse();
    }

    public Answer askQuestion(Question question) {
        ChatResponse response = chatClient.prompt()
                .user(promptUserSpec -> promptUserSpec.text(GENERAL_QUESTION)
                        .param("about", question.getAbout())
                        .param("question", question.getQuestion()))
                .call()
                .chatResponse();
        return new Answer(response.getResult().getOutput().getContent());

    }
}
