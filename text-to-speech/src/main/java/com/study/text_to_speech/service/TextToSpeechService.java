package com.study.text_to_speech.service;

import com.study.text_to_speech.model.TextToSpeechRequest;
import org.springframework.ai.openai.OpenAiAudioSpeechModel;
import org.springframework.ai.openai.audio.speech.SpeechModel;
import org.springframework.ai.openai.audio.speech.SpeechPrompt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TextToSpeechService {

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    private SpeechModel speechModel;

    public TextToSpeechService(OpenAiAudioSpeechModel speechModel) {
        this.speechModel = speechModel;
    }

    public byte[] convertTextToSpeech(TextToSpeechRequest request) {
        return speechModel.call(new SpeechPrompt(request.text())).getResult().getOutput();
    }
}
