package com.study.study_ai.services;

import com.study.study_ai.controllers.chat.requests.Question;
import com.study.study_ai.controllers.chat.responses.Answer;
import com.study.study_ai.controllers.chat.responses.FeaturesWrapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    public List<String> structuredList(Question question) {
        return chatClient.prompt()
                .user(promptUserSpec -> promptUserSpec.text(GENERAL_QUESTION)
                        .param("about", question.getAbout())
                        .param("question", question.getQuestion()))
                .call()
                .entity(new ListOutputConverter(new DefaultConversionService()));
    }

    public Map<String, FeaturesWrapper> structuredMap(Question question) {
        return chatClient.prompt()
                .user(promptUserSpec -> promptUserSpec.text(GENERAL_QUESTION)
                        .param("about", question.getAbout())
                        .param("question", question.getQuestion()))
                .call()
                .entity(new ParameterizedTypeReference<>() {});
    }
}
