package com.learn.media_generator.services;

import com.learn.media_generator.entities.MediaPost;
import com.learn.media_generator.repository.MediaRepository;
import com.learn.media_generator.requests.PersonalizedPostRequest;
import com.learn.media_generator.requests.PersonalizedPostRequest.PostDetails;
import com.learn.media_generator.response.MediaResponse;
import com.learn.media_generator.response.MediaResponse.MediaDetailsResponse;
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
import java.util.UUID;

import static com.learn.media_generator.constants.Prompts.gerFormattedPromptForFacebook;
import static com.learn.media_generator.constants.Prompts.gerFormattedPromptForLinkedIN;
import static com.learn.media_generator.constants.Prompts.gerFormattedPromptForTwitter;
import static com.learn.media_generator.utils.FileUtils.uploadFile;

@Service
public class PersonalizedPostService {

    private final MediaRepository repository;
    private final OpenAiImageModel imageModel;
    private final String uploadDir;
    private final String host;

    public PersonalizedPostService(MediaRepository repository,
                                   OpenAiImageModel imageModel,
                                   @Value("${file.upload-dir}") String uploadDir,
                                   @Value("${server.port}") String port,
                                   @Value("${server.servlet.context-path}") String contextPath) {
        this.repository = repository;
        this.imageModel = imageModel;
        this.uploadDir = uploadDir;
        this.host = "http://localhost:" + port + contextPath;
    }

    public Response generatePersonalizedPost(UUID acctId, PersonalizedPostRequest request) {

        List<MediaDetailsResponse> response = request.getPostDetails().stream()
                .flatMap(post -> {
                    OpenAiImageOptions imageOptions = OpenAiImageOptions.builder()
                            .model("gpt-image-1")
                            .N(1)
                            .quality("high")
                            .width(post.getWidth() > 0 ? post.getWidth() : 1024)
                            .height(post.getHeight() > 0 ? post.getHeight() : 1024)
                            .build();

                    String instructions = switch (request.getSocialMediaChannel()) {
                        case LINKEDIN -> gerFormattedPromptForLinkedIN(request);
                        case FACEBOOK -> gerFormattedPromptForFacebook(request);
                        case TWITTER -> gerFormattedPromptForTwitter(request);
                    };

                    ImagePrompt prompt = new ImagePrompt(instructions, imageOptions);

                    return imageModel.call(prompt).getResults().stream()
                            .map(img -> {
                                try {
                                    byte[] imageData = Base64.getDecoder().decode(img.getOutput().getB64Json());
                                    String url = uploadFile(imageData, uploadDir,
                                            post.getId() + "." + post.getImageFormat().getFormat());
                                    MediaPost savedPost = save(request, post, acctId, url);
                                    return new MediaDetailsResponse(savedPost.getId(), post.getId(), this.host + url, url);
                                } catch (IOException e) {
                                    System.out.println("Failed to upload file: " + e.getMessage());
                                    return null;
                                }
                            })
                            .filter(Objects::nonNull);

                })
                .toList();
        return MediaResponse.builder()
                .success(true)
                .responseCode(200)
                .responseMsg("Personalized post generated successfully")
                .mediaDetails(response)
                .build();
    }

    public Response getAllPersonalizedPosts(UUID acctId, UUID eventId, UUID attendeeId) {
        List<MediaPost> mediaPosts = this.repository.findByAccountMappingIdAndEventIdAndAttendeeId(acctId, eventId, attendeeId);
        List<MediaDetailsResponse> result = mediaPosts.stream()
                .map(post -> new MediaDetailsResponse(post.getId(), post.getResourceId(), this.host + post.getUrl(), post.getUrl()))
                .toList();

        return MediaResponse.builder()
                .success(true)
                .responseCode(200)
                .responseMsg("Personalized post accessed successfully")
                .mediaDetails(result)
                .build();
    }

    private MediaPost save(PersonalizedPostRequest request, PostDetails post, UUID acctId, String url) {
        MediaPost entity = new MediaPost();
        entity.setAccountMappingId(acctId);
        entity.setEventId(request.getEventId());
        entity.setResourceId(post.getId());
        entity.setAttendeeId(request.getAttendeeProfile().getAttendeeId());
        entity.setUrl(url);
        return this.repository.save(entity);
    }
}
