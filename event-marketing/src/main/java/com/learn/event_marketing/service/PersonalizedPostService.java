package com.learn.event_marketing.service;

import com.learn.event_marketing.entity.PersonalizedPostChannelContentEntity;
import com.learn.event_marketing.entity.PersonalizedPostEntity;
import com.learn.event_marketing.models.PersonalizedPost;
import com.learn.event_marketing.models.PersonalizedPost.Content;
import com.learn.event_marketing.models.PersonalizedPostResponse;
import com.learn.event_marketing.repository.PersonalizedPostRepository;
import com.learn.event_marketing.requests.PersonalizedPostRequest;
import com.learn.event_marketing.response.Response;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.learn.event_marketing.constants.Prompts.getFormattedPrompt;

@Service
public class PersonalizedPostService {

    private final ChatClient chatClient;
    private final PersonalizedPostRepository repository;

    public PersonalizedPostService(@Qualifier("personalized-media-post-chat-client") ChatClient chatClient,
                                   PersonalizedPostRepository repository) {
        this.chatClient = chatClient;
        this.repository = repository;
    }

    public Response generatePersonalizedPostContent(UUID acct_id, PersonalizedPostRequest request) {

        PersonalizedPost marketingContent = chatClient.prompt()
                .user(spec -> spec
                        .text(getFormattedPrompt(request)))
                .call()
                .entity(PersonalizedPost.class);

        PersonalizedPostEntity savedEn = repository.save(toPersonalizedPostEntity(acct_id, request, marketingContent));

        return PersonalizedPostResponse.builder()
                .success(true)
                .responseMsg("Personalized post content generated successfully")
                .responseCode(200)
                .personalizedPost(toPersonalizedPost(savedEn))
                .build();
    }

    public Response getAllPersonalizedPosts(UUID acctId, UUID eventId, UUID attendeeId) {
        return null;
    }

    private PersonalizedPostEntity toPersonalizedPostEntity(UUID acctId, PersonalizedPostRequest request,
                                                            PersonalizedPost marketingContent) {

        PersonalizedPostEntity entity = new PersonalizedPostEntity();
        entity.setAccountMappingId(acctId);
        entity.setEventId(request.getEventId());
        entity.setEventTitle(request.getTitle());
        entity.setEventDescription(request.getDescription());
        entity.setAttendeeId(request.getAttendeeProfile().getAttendeeId());
        marketingContent.getPosts()
                .forEach(content -> {
                    PersonalizedPostChannelContentEntity en = new PersonalizedPostChannelContentEntity();
                    en.setChannelName(request.getSocialMediaChannel().getType());
                    en.setTitle(content.getTitle());
                    en.setDescription(content.getDescription());
                    entity.addChannelContent(en);
                });

        return entity;
    }

    private PersonalizedPost toPersonalizedPost(PersonalizedPostEntity entity) {
        return new PersonalizedPost(
                entity.getChannelContent().stream()
                        .map(en -> {
                            Content content = new Content();
                            content.setId(en.getId());
                            content.setTitle(en.getTitle());
                            content.setDescription(en.getDescription());
                            return content;
                        })
                        .toList()
        );
    }
}
