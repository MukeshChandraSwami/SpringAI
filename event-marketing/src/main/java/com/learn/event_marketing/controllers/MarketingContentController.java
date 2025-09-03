package com.learn.event_marketing.controllers;

import com.learn.event_marketing.requests.GenerateMarketingContentRequest;
import com.learn.event_marketing.response.Response;
import com.learn.event_marketing.service.MarketingContentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.learn.event_marketing.constants.AppConstants.RoutingConstants.ACT;
import static com.learn.event_marketing.constants.AppConstants.RoutingConstants.ACT_ID;
import static com.learn.event_marketing.constants.AppConstants.RoutingConstants.EVENT;
import static com.learn.event_marketing.constants.AppConstants.RoutingConstants.EVENT_ID;

@RestController
@RequestMapping(ACT + ACT_ID)
public class MarketingContentController {

    private final MarketingContentService marketingContentService;

    public MarketingContentController(MarketingContentService marketingContentService) {
        this.marketingContentService = marketingContentService;
    }

    @PostMapping
    public Response generateMarketingContent(@PathVariable UUID acct_id,
            @RequestBody GenerateMarketingContentRequest request) {

        return this.marketingContentService.generateContent(acct_id, request);
    }

    @GetMapping(EVENT + EVENT_ID)
    public Response getAllMediaPostsOfEvent(@PathVariable UUID acct_id,
                                            @PathVariable UUID event_id) {
        return this.marketingContentService.getAllMarketingContents(acct_id, event_id);
    }
}
