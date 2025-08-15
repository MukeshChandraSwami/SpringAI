package com.learn.media_details_extractor.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.media_details_extractor.requests.MediaRequest;
import com.learn.media_details_extractor.response.MediaResponse;
import com.learn.media_details_extractor.response.Response;
import com.learn.media_details_extractor.services.MediaService;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import static com.learn.media_details_extractor.constants.AppConstants.Profiles.LMS;
import static com.learn.media_details_extractor.constants.Prompts.MEDIA_DETAILS_EXTRACTOR;
import static com.learn.media_details_extractor.constants.Prompts.USER_PROMPT;

@Service
@Profile(LMS)
public class LmsMediaService implements MediaService {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
    }

    private final String lmsUrl;

    public LmsMediaService(@Value("${lmstudio.chat-url}") String lmsUrl) {
        this.lmsUrl = lmsUrl;
    }

    @Override
    public Response extractMediaDetails( MediaRequest request) throws Exception {

        String lmStudioMediaRequest = String.format(getLmStudioMediaRequest(),
                MEDIA_DETAILS_EXTRACTOR, USER_PROMPT, request.getUrl(), 0.2F);
        getImageDetails(lmStudioMediaRequest);

        return MediaResponse.builder()
                .success(true)
                .responseCode(200)
                .build();
    }

    private void getImageDetails(String requestBody) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPostRequest = new HttpPost(lmsUrl);
            httpPostRequest.setEntity(new StringEntity(requestBody, ContentType.APPLICATION_JSON));
            CloseableHttpResponse httpResponse = client.execute(httpPostRequest);
            JsonNode root = objectMapper.readTree(httpResponse.getEntity().getContent());
            System.out.println("Response: " + root.toPrettyString());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get image details", e);
        }
    }

    private String getLmStudioMediaRequest() {
        return """
                {
                  "model": "google/gemma-3-27b",
                  "messages": [
                    {
                      "role": "system",
                      "content": "%s"
                    },
                    {
                      "role": "user",
                      "content": %s
                    },
                    {
                        "type": "image_url",
                        "image_url": {
                           "url": "%s"
                        }
                    }
                  ],
                  "temperature": %f
                }
                """;
    }
}
