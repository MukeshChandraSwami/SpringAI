package com.learn.media_details_extractor.services.impl;

import com.learn.media_details_extractor.models.ImageAnalysis;
import com.learn.media_details_extractor.response.MediaResponse;
import com.learn.media_details_extractor.response.Response;
import com.learn.media_details_extractor.services.MediaService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.web.multipart.MultipartFile;

import static com.learn.media_details_extractor.constants.Prompts.USER_PROMPT;
import static org.springframework.ai.model.SpringAIModels.OPENAI;

@Service
@Profile(OPENAI)
public class OpenAiMediaService implements MediaService {

    private final ChatClient chatClient;

    public OpenAiMediaService(@Qualifier("media-details-extractor-openai-client") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public Response extractMediaDetails(MultipartFile file) throws Exception {

        Resource imageResource = new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        };

        ImageAnalysis imageData = chatClient.prompt()
                .user(spec -> spec
                        .text(USER_PROMPT)
                        .media(MimeType.valueOf(file.getContentType()), imageResource))
                .call()
                .entity(ImageAnalysis.class);

        return MediaResponse.builder()
                .success(true)
                .responseCode(200)
                .responseMsg("Media details extraction not implemented for this service")
                .imageAnalysis(imageData)
                .build();
    }
}
