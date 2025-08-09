package com.learn.ml_chat_boat.controllers;

import com.learn.ml_chat_boat.request.TranslationsRequest;
import com.learn.ml_chat_boat.response.TranslationsResponse;
import com.learn.ml_chat_boat.service.TranslationsService;
import com.learn.ml_chat_boat.service.impl.OpenAiTranslationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static com.learn.ml_chat_boat.constants.AppConstants.RoutingConstants.TRANSLATE;

@RestController
@RequestMapping(TRANSLATE)
public class TranslationsController {

    @Autowired
    private TranslationsService translationsService;


    @PostMapping
    public TranslationsResponse translate(@RequestBody TranslationsRequest request) {
        Map<String, List<String>> translations = translationsService.translate(request);
        return TranslationsResponse.builder()
                .translations(translations)
                .success(true)
                .responseCode(200)
                .responseMsg("Translated Successfully")
                .build();
    }
}
