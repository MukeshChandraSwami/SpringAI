package com.learn.event_marketing.service;

import com.learn.event_marketing.entity.ChannelContentEntity;
import com.learn.event_marketing.entity.MarketingContentEntity;
import com.learn.event_marketing.models.MarketingContent;
import com.learn.event_marketing.repository.MarketingContentRepository;
import com.learn.event_marketing.requests.GenerateMarketingContentRequest;
import com.learn.event_marketing.response.EventMarketingResponse;
import com.learn.event_marketing.response.MediaResponse;
import com.learn.event_marketing.response.Response;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.learn.event_marketing.constants.Prompts.getFormattedPrompt;

@Service
public class MarketingContentService {

    private final ChatClient chatClient;
    private final MediaGenerationService mediaGenerationService;
    private final MarketingContentRepository marketingContentRepository;

    public MarketingContentService(@Qualifier("marketing-chat-client") ChatClient chatClient,
                                   MediaGenerationService mediaGenerationService,
            MarketingContentRepository marketingContentRepository) {
        this.chatClient = chatClient;
        this.mediaGenerationService = mediaGenerationService;
        this.marketingContentRepository = marketingContentRepository;
    }

    public Response generateContent(UUID acct_id, GenerateMarketingContentRequest request) {

        MarketingContent marketingContent = chatClient.prompt()
                .user(spec -> spec
                        .text(getFormattedPrompt(request)))
                .call()
                .entity(MarketingContent.class);

        MarketingContentEntity savedEntity = marketingContentRepository.save(getmarketingContentEntity(acct_id, request, marketingContent));

        new Thread(() -> mediaGenerationService.generateMediaForMarketingContent(acct_id, request, getPostsForMediaGeneration(savedEntity))).start();

        return EventMarketingResponse.builder()
                .success(true)
                .responseCode(200)
                .marketingContent(toMarketingContent(savedEntity, null))
                .responseMsg("Marketing content generated successfully")
                .build();
    }

    public Response getAllMarketingContents(UUID acct_id, UUID event_id) {
        MarketingContentEntity marketingContentEntity = marketingContentRepository.findByAccountMappingIdAndEventId(acct_id, event_id);
        MediaResponse postsForMarketingContent = mediaGenerationService.getSocialMediaPostsForMarketingContent(acct_id, event_id);
        return EventMarketingResponse.builder()
                .success(true)
                .responseCode(200)
                .marketingContent(toMarketingContent(marketingContentEntity, postsForMarketingContent))
                .responseMsg("Marketing contents fetched successfully")
                .build();
    }

    private MarketingContentEntity getmarketingContentEntity(UUID acctId, GenerateMarketingContentRequest request, MarketingContent marketingContent) {

        MarketingContentEntity entity = new MarketingContentEntity();

        entity.setAccountMappingId(acctId);
        entity.setEventId(request.getEventId());
        entity.setEventTitle(marketingContent.getEventTitle());
        entity.setEventDescription(marketingContent.getEventDescription());
        entity.setEventStatus(request.getEventStatus().getType());
        entity.setSeoKeywordsAndDescription(marketingContent.getSeoKeywordsAndDescription());

        marketingContent.getChannelContent().forEach((channelName, contents) -> {
            contents.forEach(content -> {
                ChannelContentEntity channelContentEntity = new ChannelContentEntity();
                channelContentEntity.setChannelName(channelName);
                channelContentEntity.setTitle(content.getTitle());
                channelContentEntity.setDescription(content.getDescription());
                channelContentEntity.setDateAndTimeToPost(content.getDateAndTimeToPost());
                entity.addChannelContent(channelContentEntity);
            });
        });

        return entity;
    }

    private List<ChannelContentEntity> getPostsForMediaGeneration(MarketingContentEntity marketingContentEntity) {

        return marketingContentEntity.getChannelContent().stream()
                .filter(c -> c.getChannelName().equalsIgnoreCase("LinkedIn"))
                .toList();
    }

    private MarketingContent toMarketingContent(MarketingContentEntity entity, MediaResponse postsForMarketingContent) {
        MarketingContent marketingContent = new MarketingContent();
        marketingContent.setEventTitle(entity.getEventTitle());
        marketingContent.setEventDescription(entity.getEventDescription());
        marketingContent.setSeoKeywordsAndDescription(entity.getSeoKeywordsAndDescription());

        // Assuming getChannelContent() returns a List<ChannelContentEntity>
        Map<String, List<MarketingContent.Content>> channelContentMap = new HashMap<>();
        for (ChannelContentEntity channelEntity : entity.getChannelContent()) {
            MarketingContent.Content content = new MarketingContent.Content();
            content.setId(channelEntity.getId());
            content.setTitle(channelEntity.getTitle());
            content.setDescription(channelEntity.getDescription());
            content.setDateAndTimeToPost(channelEntity.getDateAndTimeToPost());

            if (postsForMarketingContent != null) {
                List<String> mediaUrls = postsForMarketingContent.getMediaDetails().stream()
                        .filter(media -> media.getResourceId().equals(channelEntity.getId()))
                        .map(MediaResponse.MediaDetailsResponse::getUrl)
                        .toList();
                content.setPosts(mediaUrls);
            }

            channelContentMap
                    .computeIfAbsent(channelEntity.getChannelName(), k -> new ArrayList<>())
                    .add(content);
        }
        marketingContent.setChannelContent(channelContentMap);

        return marketingContent;
    }
}
