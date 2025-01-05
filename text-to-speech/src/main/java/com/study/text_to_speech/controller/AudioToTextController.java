package com.study.text_to_speech.controller;

import com.study.text_to_speech.service.AudioToTextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/audio-to-text")
public class AudioToTextController {

    @Autowired
    private AudioToTextService audioToTextService;

    @PostMapping
    public ResponseEntity<String> uploadMp3(@RequestParam("file") MultipartFile file) {
        String text = audioToTextService.convertAudioToText(file);
        return ResponseEntity.ok(text);
    }
}
