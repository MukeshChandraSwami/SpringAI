package com.learn.event_marketing.service;

import com.learn.event_marketing.entity.PersonalizedPostChannelContentEntity;
import com.learn.event_marketing.entity.PersonalizedPostEntity;
import com.learn.event_marketing.models.AiPersonalizedPostResponse;
import com.learn.event_marketing.models.PersonalizedPost;
import com.learn.event_marketing.models.PersonalizedPost.Content;
import com.learn.event_marketing.repository.PersonalizedPostRepository;
import com.learn.event_marketing.requests.PersonalizedPostRequest;
import com.learn.event_marketing.response.MediaResponse;
import com.learn.event_marketing.response.PersonalizedPostResponse;
import com.learn.event_marketing.response.Response;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.learn.event_marketing.constants.Prompts.getFormattedPrompt;

@Service
public class PersonalizedPostService {

    private final ChatClient chatClient;
    private final PersonalizedPostRepository repository;
    private final MediaGenerationService mediaGenerationService;

    public PersonalizedPostService(@Qualifier("personalized-media-post-chat-client") ChatClient chatClient,
                                   PersonalizedPostRepository repository,
                                   MediaGenerationService mediaGenerationService) {
        this.chatClient = chatClient;
        this.repository = repository;
        this.mediaGenerationService = mediaGenerationService;
    }

    public Response generatePersonalizedPostContent(UUID acct_id, PersonalizedPostRequest request) {

        AiPersonalizedPostResponse marketingContent = chatClient.prompt()
                .user(spec -> spec
                        .text(getFormattedPrompt(request)))
                .call()
                .entity(AiPersonalizedPostResponse.class);

        PersonalizedPostEntity savedEn = repository.save(toPersonalizedPostEntity(acct_id, request, marketingContent));

        new Thread(() -> mediaGenerationService.generateMediaForPersonalizedPost(acct_id, request, savedEn)).start();

        return PersonalizedPostResponse.builder()
                .success(true)
                .responseMsg("Personalized post content generated successfully")
                .responseCode(200)
                .personalizedPost(toPersonalizedPostResponse(List.of(savedEn), null))
                .build();
    }

    public Response getAllPersonalizedPosts(UUID acctId, UUID eventId, UUID attendeeId) {
        List<PersonalizedPostEntity> personalizedPostContent = this.repository.findByAccountMappingIdAndEventIdAndAttendeeId(acctId, eventId, attendeeId);
        MediaResponse personalizedPosts = this.mediaGenerationService.getAllPersonalizedPosts(acctId, eventId, attendeeId);
        return PersonalizedPostResponse.builder()
                .success(true)
                .responseCode(200)
                .personalizedPost(toPersonalizedPostResponse(personalizedPostContent, personalizedPosts))
                .responseMsg("Personalized post contents fetched successfully")
                .build();
    }

    private PersonalizedPostEntity toPersonalizedPostEntity(UUID acctId, PersonalizedPostRequest request,
                                                            AiPersonalizedPostResponse marketingContent) {

        PersonalizedPostEntity entity = new PersonalizedPostEntity();
        entity.setAccountMappingId(acctId);
        entity.setEventId(request.getEventId());
        entity.setEventTitle(request.getTitle());
        entity.setEventDescription(request.getDescription());
        entity.setAttendeeId(request.getAttendeeProfile().getAttendeeId());
        marketingContent.getPosts()
                .forEach(p -> {
                    PersonalizedPostChannelContentEntity en = new PersonalizedPostChannelContentEntity();
                    en.setChannelName(request.getSocialMediaChannel().getType());
                    en.setTitle(p.getTitle());
                    en.setDescription(p.getDescription());
                    entity.addChannelContent(en);
                });

        return entity;
    }

    private PersonalizedPost toPersonalizedPostResponse(List<PersonalizedPostEntity> entity, MediaResponse personalizedPosts) {
        return new PersonalizedPost(
                entity.stream()
                        .flatMap(en -> en.getChannelContent().stream())
                        .map(c -> {
                            Content content = new Content();
                            content.setId(c.getId());
                            content.setTitle(c.getTitle());
                            content.setDescription(c.getDescription());
                            content.setChannel(c.getChannelName());
                            if (personalizedPosts != null) {
                                content.setImageUrls(personalizedPosts.getMediaDetails()
                                        .stream()
                                        .filter(md -> md.getResourceId().equals(c.getId()))
                                        .map(MediaResponse.MediaDetailsResponse::getUrl)
                                        .toList());
                            }
                            return content;
                        })
                        .collect(Collectors.groupingBy(Content::getChannel))
        );
    }
}
