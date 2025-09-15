package com.learn.event_marketing.service;

import com.learn.event_marketing.entity.ChannelContentEntity;
import com.learn.event_marketing.entity.PersonalizedPostEntity;
import com.learn.event_marketing.requests.GenerateMarketingContentRequest;
import com.learn.event_marketing.requests.MediaRequest;
import com.learn.event_marketing.requests.MediaRequest.MediaDetails;
import com.learn.event_marketing.requests.PersonalizedPostMediaRequest;
import com.learn.event_marketing.requests.PersonalizedPostMediaRequest.PostDetails;
import com.learn.event_marketing.requests.PersonalizedPostRequest;
import com.learn.event_marketing.response.MediaResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

import static com.learn.event_marketing.constants.AppConstants.RoutingConstants.ACT;
import static com.learn.event_marketing.constants.AppConstants.RoutingConstants.ATTENDE;
import static com.learn.event_marketing.constants.AppConstants.RoutingConstants.EVENT;
import static com.learn.event_marketing.constants.AppConstants.RoutingConstants.POST;

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

    public MediaResponse getSocialMediaPostsForMarketingContent(UUID acctId, UUID eventId) {
        ResponseEntity<MediaResponse> mediaResponse =
                restTemplate.getForEntity(ACT + "/" + acctId + EVENT + "/" + eventId, MediaResponse.class);
        MediaResponse body = mediaResponse.getBody();
        if (mediaResponse.getStatusCode().value() == 200 && body != null) {
            return body;
        }

        throw new RuntimeException("Error while fetching media for event: " + eventId);
    }

    public void generateMediaForPersonalizedPost(UUID acctId, PersonalizedPostRequest request, PersonalizedPostEntity entity) {
        PersonalizedPostMediaRequest mediaRequest = getPersonalizedPostMediaRequest(request, entity);

        try {
            MediaResponse mediaResponse = restTemplate.postForObject(ACT + "/" + acctId + POST,
                    mediaRequest, MediaResponse.class);
            System.out.println(mediaResponse);
        } catch (Exception e) {
            throw new RuntimeException("Error while creating personalized posts: " + e);
        }
    }

    public MediaResponse getAllPersonalizedPosts(UUID acctId, UUID eventId, UUID attendeeId) {
        ResponseEntity<MediaResponse> mediaResponse =
                restTemplate.getForEntity(ACT + "/" + acctId + EVENT + "/" + eventId + ATTENDE + "/" + attendeeId + POST, MediaResponse.class);
        MediaResponse body = mediaResponse.getBody();
        if (mediaResponse.getStatusCode().value() == 200 && body != null) {
            return body;
        }

        throw new RuntimeException("Error while fetching personalized posts for event: " + eventId);
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

    private PersonalizedPostMediaRequest getPersonalizedPostMediaRequest(PersonalizedPostRequest request, PersonalizedPostEntity entity) {
        PersonalizedPostMediaRequest mediaRequest = new PersonalizedPostMediaRequest();

        mediaRequest.setEventId(request.getEventId());
        mediaRequest.setTitle(request.getTitle());
        mediaRequest.setDescription(request.getDescription());
        mediaRequest.setEventDateAndTime(request.getEventDateAndTime());
        mediaRequest.setAttendeeProfile(request.getAttendeeProfile());
        mediaRequest.setSocialMediaChannel(request.getSocialMediaChannel());
        mediaRequest.setThemingDetails(request.getThemingDetails());
        mediaRequest.setPostDetails(entity.getChannelContent()
                .stream()
                .map(content -> {
                    return PostDetails.builder()
                            .id(content.getId())
                            .title(content.getTitle())
                            .description(content.getDescription())
                            .height(1024)
                            .width(1024)
                            .imageFormat(PersonalizedPostMediaRequest.ImageFormat.JPEG)
                            .build();
                }).toList()
        );

        return mediaRequest;
    }
}
