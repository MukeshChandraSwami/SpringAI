package com.learn.media_generator.services;

import com.learn.media_generator.constants.Prompts;
import com.learn.media_generator.requests.MediaRequest;
import com.learn.media_generator.response.ImageGenerationResponse;
import com.learn.media_generator.response.ImageGenerationResponse.MediaDetailsResponse;
import com.learn.media_generator.response.Response;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

import static com.learn.media_generator.utils.FileUtils.uploadFile;

@Service
public class MediaService {

    private final OpenAiImageModel imageModel;
    private final String uploadDir;
    private final String host;

    public MediaService(OpenAiImageModel imageModel,
                        @Value("${file.upload-dir}") String uploadDir,
                        @Value("${server.port}") String port,
                        @Value("${server.servlet.context-path") String contextPath) {
        this.imageModel = imageModel;
        this.uploadDir = uploadDir;
        this.host = "http://localhost:" + port + contextPath;
    }

    public Response generateMedia(MediaRequest request) {

        List<MediaDetailsResponse> result = request.getMediaDetails()
                .stream()
                .flatMap(mediaDetail -> {
                    // Build image options with default dimensions if not specified
                    OpenAiImageOptions imageOptions = OpenAiImageOptions.builder()
                            .model("gpt-image-1")
                            .N(2)
                            .quality("high")
                            .width(mediaDetail.getWidth() > 0 ? mediaDetail.getWidth() : 1024)
                            .height(mediaDetail.getHeight() > 0 ? mediaDetail.getHeight() : 1024)
                            .build();

                    // Generate images
                    ImagePrompt prompt = new ImagePrompt(Prompts.getFormattedPrompt(mediaDetail), imageOptions);
                    return imageModel.call(prompt).getResults().stream()
                            .map(img -> {
                                try {
                                    byte[] imageData = Base64.getDecoder().decode(img.getOutput().getB64Json());
                                    String url = uploadFile(imageData, uploadDir,
                                            mediaDetail.getId() + "." + mediaDetail.getFormat().getFormat());
                                    return new MediaDetailsResponse(mediaDetail.getId(), host + url, url);
                                } catch (IOException e) {
                                    System.out.println("Failed to upload file: " + e.getMessage());
                                    return null;
                                }
                            })
                            .filter(Objects::nonNull);
                })
                .toList();


        return ImageGenerationResponse.builder()
                .success(true)
                .responseCode(200)
                .responseMsg("Media generated successfully")
                .generatedImages(result)
                .build();
    }
}
