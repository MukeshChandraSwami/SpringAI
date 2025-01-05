package com.study.text_to_speech.controller;

import com.study.text_to_speech.model.TextToSpeechRequest;
import com.study.text_to_speech.service.TextToSpeechService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/text-to-speech")
public class TextToSpeechController {

    @Autowired
    private TextToSpeechService textToSpeechService;

    @PostMapping
    public byte[] textToSpeech(@RequestBody TextToSpeechRequest text) {
        return textToSpeechService.convertTextToSpeech(text);
    }
}
