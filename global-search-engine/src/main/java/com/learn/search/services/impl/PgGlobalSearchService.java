package com.learn.search.services.impl;

import com.learn.search.entities.ResourceDataEmbeddingsEntity;
import com.learn.search.models.SearchResult;
import com.learn.search.repositories.PgGlobalSearchRepository;
import com.learn.search.requests.EmbeddingRequest;
import com.learn.search.requests.GlobalSearchRequest;
import com.learn.search.response.EmbeddingResponse;
import com.learn.search.response.SearchResponse;
import com.learn.search.services.GlobalSearchService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.learn.search.constants.AppConstants.Profiles.PG;


@Service
@Profile(PG)
public class PgGlobalSearchService implements GlobalSearchService {

    private final String embeddingsUrl;
    private final PgGlobalSearchRepository repository;

    public PgGlobalSearchService(@Value("${lmstudio.embeddings-url}") String embeddingsUrl,
            PgGlobalSearchRepository repository) {
        this.embeddingsUrl = embeddingsUrl;
        this.repository = repository;
    }

    @Override
    public EmbeddingResponse save(UUID acct_id, EmbeddingRequest request) {

        UUID id = UUID.randomUUID();
        try {
            ResourceDataEmbeddingsEntity entity = ResourceDataEmbeddingsEntity.builder()
                    .accountMappingId(acct_id)
                    .id(id)
                    .content(request.getContent())
                    .resourceId(request.getResourceId())
                    .resourceType(request.getResourceType())
                    .build();

            repository.save(entity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return EmbeddingResponse.builder()
                .success(true)
                .responseMsg("Data Saved")
                .responseCode(200)
                .resourceId(request.getResourceId())
                .content(request.getContent())
                .resourceType(request.getResourceType())
                .id(id)
                .build();
    }

    @Override
    public SearchResponse search(UUID acct_id, GlobalSearchRequest request) {
        List<SearchResult> searchDataResponses = new ArrayList<>();
        try {
            List<ResourceDataEmbeddingsEntity> search = repository.search(acct_id, request, 100);
            search.forEach(entity -> {
                SearchResult data = SearchResult.builder()
                        .id(entity.getId())
                        .resourceId(entity.getResourceId())
                        .content(entity.getContent())
                        .resourceType(entity.getResourceType())
                        .build();
                searchDataResponses.add(data);
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return SearchResponse.builder()
                .success(true)
                .responseMsg("Searched Data for query:- " + request.getQuery())
                .responseCode(200)
                .searchResponses(searchDataResponses)
                .build();
    }
}
