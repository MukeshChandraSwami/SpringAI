package com.learn.ml_chat_boat.service;

import com.learn.ml_chat_boat.request.TranslationsRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface TranslationsService {

    public Map<String, List<String>> translate(TranslationsRequest request);
}
