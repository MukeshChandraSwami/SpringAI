package com.learn.search.services;


import com.learn.search.requests.CreateSearchDataRequest;
import com.learn.search.requests.GlobalSearchRequest;
import com.learn.search.response.CreateSearchDataResponse;
import com.learn.search.response.SearchResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface GlobalSearchService {

    CreateSearchDataResponse save(UUID acct_id, CreateSearchDataRequest request);

    SearchResponse search(UUID acct_id, GlobalSearchRequest request);
}
