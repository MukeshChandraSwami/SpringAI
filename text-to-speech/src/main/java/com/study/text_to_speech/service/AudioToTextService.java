package com.study.text_to_speech.service;

import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AudioToTextService {

    private OpenAiAudioTranscriptionModel transcriptionModel;

    public AudioToTextService(OpenAiAudioTranscriptionModel transcriptionModel) {
        this.transcriptionModel = transcriptionModel;
    }

    public String convertAudioToText(MultipartFile fileBytes) {
        return transcriptionModel.call(new AudioTranscriptionPrompt(fileBytes.getResource())).getResult().getOutput();
    }
}
