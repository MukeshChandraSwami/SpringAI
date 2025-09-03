package com.learn.event_marketing.service;

import com.learn.event_marketing.entity.ChannelContentEntity;
import com.learn.event_marketing.entity.MarketingContentEntity;
import com.learn.event_marketing.models.MarketingContent;
import com.learn.event_marketing.repository.MarketingContentRepository;
import com.learn.event_marketing.requests.GenerateMarketingContentRequest;
import com.learn.event_marketing.response.EventMarketingResponse;
import com.learn.event_marketing.response.Response;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.learn.event_marketing.constants.Prompts.getFormattedPrompt;

@Service
public class MarketingContentService {

    private final ChatClient chatClient;
    private final MarketingContentRepository marketingContentRepository;

    public MarketingContentService(ChatClient chatClient,
            MarketingContentRepository marketingContentRepository) {
        this.chatClient = chatClient;
        this.marketingContentRepository = marketingContentRepository;
    }

    public Response generateContent(UUID acct_id, GenerateMarketingContentRequest request) {

        MarketingContent marketingContent = chatClient.prompt()
                .user(spec -> spec
                        .text(getFormattedPrompt(request)))
                .call()
                .entity(MarketingContent.class);

        MarketingContentEntity savedEntity = marketingContentRepository.save(getmarketingContentEntity(acct_id, request, marketingContent));

        return EventMarketingResponse.builder()
                .success(true)
                .responseCode(200)
                .marketingContent(marketingContent)
                .responseMsg("Marketing content generated successfully")
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
}
