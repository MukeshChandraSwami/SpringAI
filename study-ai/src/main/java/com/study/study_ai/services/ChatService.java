package com.study.study_ai.services;

import com.study.study_ai.controllers.chat.requests.Question;
import com.study.study_ai.controllers.chat.responses.Answer;
import com.study.study_ai.controllers.chat.responses.FeaturesWrapper;
import com.study.study_ai.converter.StringToMapWithPojo;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class ChatService {

    private static final String GENERAL_QUESTION = """
            Explain {about} for the {question}?
            """;

    @Autowired
    private ChatClient chatClient;

    @Autowired
    private StringToMapWithPojo<String, FeaturesWrapper> converter;

    public String generate(Question question) {
        return chatClient.prompt()
                .user(question.question())
                .call()
                .chatResponse()
                .getResult()
                .getOutput()
                .getContent();
    }

    public ChatResponse fullResponse(Question question) {
        return chatClient.prompt()
                .user(question.question())
                .call()
                .chatResponse();
    }

    public Answer askQuestion(Question question) {
        ChatResponse response = chatClient.prompt()
                .user(promptUserSpec -> promptUserSpec.text(GENERAL_QUESTION)
                        .param("about", question.about())
                        .param("question", question.question()))
                .call()
                .chatResponse();
        return new Answer(response.getResult().getOutput().getContent());

    }

    public List<String> structuredList(Question question) {
        return chatClient.prompt()
                .user(promptUserSpec -> promptUserSpec.text(GENERAL_QUESTION)
                        .param("about", question.about())
                        .param("question", question.question()))
                .call()
                .entity(new ListOutputConverter(new DefaultConversionService()));
    }

    public Map<String, FeaturesWrapper> structuredMap(Question question) {
        return chatClient.prompt()
                .user(promptUserSpec -> promptUserSpec.text(GENERAL_QUESTION)
                        .param("about", question.about())
                        .param("question", question.question()))
                .call()
                .entity(new ParameterizedTypeReference<>() {});
    }

    public Map<String, FeaturesWrapper> structuredMapByCustomConverter(Question question) {
        String format = converter.getFormat();
        PromptTemplate promptTemplate = new PromptTemplate(GENERAL_QUESTION);
        Prompt prompt = promptTemplate.create(Map.of("about", question.about(),
                "question", question.question(),
                "format", format));

        Generation result = chatClient.prompt(prompt).call().chatResponse().getResult();
        return converter.convert(result.getOutput().getContent());
    }


}
