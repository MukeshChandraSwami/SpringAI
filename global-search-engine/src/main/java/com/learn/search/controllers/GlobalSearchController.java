package com.learn.search.controllers;

import com.learn.search.requests.CreateSearchDataRequest;
import com.learn.search.requests.GlobalSearchRequest;
import com.learn.search.response.Response;
import com.learn.search.response.SearchResponse;
import com.learn.search.services.GlobalSearchService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.learn.search.constants.AppConstants.RoutingConstants.ACT;
import static com.learn.search.constants.AppConstants.RoutingConstants.ACT_ID;
import static com.learn.search.constants.AppConstants.RoutingConstants.SEARCH;

@RestController
@RequestMapping(ACT + ACT_ID)
public class GlobalSearchController {

    private final GlobalSearchService searchService;

    public GlobalSearchController(GlobalSearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping
    public Response createSearchData(
            @PathVariable UUID acct_id,
            @RequestBody CreateSearchDataRequest request) {

        return searchService.save(acct_id, request);
    }

    @PostMapping(SEARCH)
    public Response search(@PathVariable UUID acct_id,
                           @RequestBody GlobalSearchRequest request) {
        return searchService.search(acct_id, request);
    }
}
