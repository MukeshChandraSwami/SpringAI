package com.learn.event_marketing.service;

import com.learn.event_marketing.entity.ChannelContentEntity;
import com.learn.event_marketing.requests.GenerateMarketingContentRequest;
import com.learn.event_marketing.requests.MediaRequest;
import com.learn.event_marketing.requests.MediaRequest.MediaDetails;
import com.learn.event_marketing.response.MediaResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

import static com.learn.event_marketing.constants.AppConstants.RoutingConstants.ACT;
import static com.learn.event_marketing.constants.AppConstants.RoutingConstants.EVENT;

@Service
public class MediaGenerationService {

    private final RestTemplate restTemplate;

    public MediaGenerationService(@Qualifier("media-generator-rest-template") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void generateMediaForMarketingContent(UUID acctId, GenerateMarketingContentRequest request, List<ChannelContentEntity> postsForMediaGeneration) {
        MediaRequest mediaRequest = MediaRequest.builder()
                .mediaDetails(getMediaDetails(request, postsForMediaGeneration))
                .build();

        try {
            MediaResponse mediaResponse = restTemplate.postForObject(ACT + "/" + acctId,
                    mediaRequest, MediaResponse.class);
            System.out.println(mediaResponse);
        } catch (Exception e) {
            throw new RuntimeException("Error while creating media: " + e);
        }
    }

    private List<MediaDetails> getMediaDetails(GenerateMarketingContentRequest request, List<ChannelContentEntity> postsForMediaGeneration) {
        return postsForMediaGeneration.stream()
                .map(post -> MediaDetails.builder()
                        .id(post.getId())
                        .eventId(request.getEventId())
                        .title(request.getTitle())
                        .description(request.getDescription())
                        .eventDateAndTime(request.getEventDateAndTime())
                        .industry(request.getIndustryType())
                        .audience(request.getAudience())
                        .mediaType(MediaRequest.MediaType.IMAGE)
                        .eventType(MediaRequest.EventType.fromValue(request.getEventType().getType()))
                        .eventStatus(MediaRequest.EventStatus.fromValue(request.getEventStatus().getType()))
                        .demography(request.getDemography())
                        .socialMediaPlatform("LinkedIn")
                        .socialMediaPostTitle(post.getTitle())
                        .socialMediaPostContent(post.getDescription())
                        .format(MediaRequest.ImageFormat.WEBP)
                        .width(1024)
                        .height(1024)
                        .build())
                .toList();

    }

    public MediaResponse getSocialMediaPostsForMarketingContent(UUID acctId, UUID eventId) {
        ResponseEntity<MediaResponse> mediaResponse =
                restTemplate.getForEntity(ACT + "/" + acctId + EVENT + "/" + eventId, MediaResponse.class);
        MediaResponse body = mediaResponse.getBody();
        if (mediaResponse.getStatusCode().value() == 200 && body != null) {
            return body;
        }

        throw new RuntimeException("Error while fetching media for event: " + eventId);
    }
}
