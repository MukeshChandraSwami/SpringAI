package com.learn.event_marketing.service;

import com.learn.event_marketing.repository.MarketingContentRepository;
import com.learn.event_marketing.requests.GenerateMarketingContentRequest;
import com.learn.event_marketing.response.Response;
import org.springframework.stereotype.Service;

@Service
public class MarketingContentService {

    private final MarketingContentRepository marketingContentRepository;

    public MarketingContentService(MarketingContentRepository marketingContentRepository) {
        this.marketingContentRepository = marketingContentRepository;
    }

    public Response generateContent(GenerateMarketingContentRequest request) {
        return Response.builder()
                .success(true)
                .responseCode(200)
                .responseMsg("Marketing content generated successfully")
                .build();
    }
}
