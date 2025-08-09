package com.learn.ml_chat_boat.service.impl;

import com.learn.ml_chat_boat.request.TranslationsRequest;
import com.learn.ml_chat_boat.service.TranslationsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TranslationsServiceImpl implements TranslationsService {

    @Override
    public Map<String, List<String>> translate(TranslationsRequest request) {
        return Map.of("en-US", List.of("abc", "xyz"));
    }
}
