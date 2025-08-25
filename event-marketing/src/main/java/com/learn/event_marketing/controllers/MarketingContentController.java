package com.learn.event_marketing.controllers;

import com.learn.event_marketing.requests.GenerateMarketingContentRequest;
import com.learn.event_marketing.response.Response;
import com.learn.event_marketing.service.MarketingContentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.learn.event_marketing.constants.AppConstants.RoutingConstants.ACT;
import static com.learn.event_marketing.constants.AppConstants.RoutingConstants.ACT_ID;

@RestController
@RequestMapping(ACT + ACT_ID)
public class MarketingContentController {

    private final MarketingContentService marketingContentService;

    public MarketingContentController(MarketingContentService marketingContentService) {
        this.marketingContentService = marketingContentService;
    }

    @PostMapping
    public Response generateMarketingContent(@RequestBody GenerateMarketingContentRequest request) {

        return this.marketingContentService.generateContent(request);
    }
}
