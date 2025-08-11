package com.learn.search.services.impl;

import com.learn.search.entities.ResourceDataEmbeddingsEntity;
import com.learn.search.repositories.PgGlobalSearchRepository;
import com.learn.search.requests.CreateSearchDataRequest;
import com.learn.search.requests.GlobalSearchRequest;
import com.learn.search.response.CreateSearchDataResponse;
import com.learn.search.response.SearchResponse;
import com.learn.search.services.GlobalSearchService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.learn.search.constants.AppConstants.Profiles.PG;
import static com.learn.search.utils.EmbeddingUtils.generateEmbedding;


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
    public CreateSearchDataResponse save(UUID acct_id, CreateSearchDataRequest request) {

        ResourceDataEmbeddingsEntity savedEntity = null;
        try {
            float[] embedding = generateEmbedding(embeddingsUrl, request.getContent());
            ResourceDataEmbeddingsEntity entity = ResourceDataEmbeddingsEntity.builder()
                    .accountMappingId(acct_id)
                    .content(request.getContent())
                    .embedding(embedding)
                    .resourceId(request.getResourceId())
                    .resourceType(request.getResourceType())
                    .build();

            savedEntity = repository.save(entity);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return CreateSearchDataResponse.builder()
                .success(true)
                .responseMsg("Data Saved")
                .responseCode(200)
                .resourceId(request.getResourceId())
                .content(request.getContent())
                .id(savedEntity.getId())
                .build();
    }

    @Override
    public SearchResponse search(UUID acct_id, GlobalSearchRequest request) {
        try {
            float[] embedding = generateEmbedding(embeddingsUrl, request.getQuery());
            String embeddingString = Arrays.toString(embedding);
            List<ResourceDataEmbeddingsEntity> searchResult = repository.search(acct_id,
                    request.getResourceType(), embeddingString, 100);
            System.out.println(searchResult);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
