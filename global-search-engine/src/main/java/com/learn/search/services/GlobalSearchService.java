package com.learn.search.services;


import com.learn.search.requests.EmbeddingRequest;
import com.learn.search.requests.GlobalSearchRequest;
import com.learn.search.response.EmbeddingResponse;
import com.learn.search.response.SearchResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface GlobalSearchService {

    EmbeddingResponse save(UUID acct_id, EmbeddingRequest request);

    SearchResponse search(UUID acct_id, GlobalSearchRequest request);
}
