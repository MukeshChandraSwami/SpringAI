package com.learn.search.repositories;

import com.learn.search.constants.ResourceTypes;
import com.learn.search.entities.ResourceDataEmbeddingsEntity;
import com.learn.search.requests.GlobalSearchRequest;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.learn.search.constants.AppConstants.MetadataConstants.ACCOUNT_MAPPING_ID;
import static com.learn.search.constants.AppConstants.MetadataConstants.RESOURCE_ID;
import static com.learn.search.constants.AppConstants.MetadataConstants.RESOURCE_TYPE;


@Repository
public class PgGlobalSearchRepository {

    private final PgVectorStore pgVectorStore;
    private final FilterExpressionBuilder filterExpressionBuilder;

    public PgGlobalSearchRepository(PgVectorStore pgVectorStore,
                                    FilterExpressionBuilder filterExpressionBuilder) {
        this.pgVectorStore = pgVectorStore;
        this.filterExpressionBuilder = filterExpressionBuilder;
    }

    public void save(ResourceDataEmbeddingsEntity entity) {
        Document doc = Document.builder()
                .id(entity.getId().toString())
                .text(entity.getContent())
                .metadata(Map.of(
                        ACCOUNT_MAPPING_ID, entity.getAccountMappingId().toString(),
                        RESOURCE_ID, entity.getResourceId().toString(),
                        RESOURCE_TYPE, entity.getResourceType().getValue()))
                .build();
        pgVectorStore.add(List.of(doc));
    }

    public List<ResourceDataEmbeddingsEntity> search(UUID acctId, GlobalSearchRequest request, int limit) {
        List<Document> documents = pgVectorStore.similaritySearch(SearchRequest.builder()
                .query(request.getQuery())
                .topK(limit)
                .similarityThreshold(0.75)
                .filterExpression(filterExpressionBuilder.and(
                        filterExpressionBuilder.eq(ACCOUNT_MAPPING_ID, acctId.toString()),
                        filterExpressionBuilder.eq(RESOURCE_TYPE, request.getResourceType().getValue())
                ).build())
                .build());

        return documents.stream()
                .map(document -> ResourceDataEmbeddingsEntity.builder()
                        .id(UUID.fromString(document.getId()))
                        .content(document.getText())
                        .resourceType(ResourceTypes.fromValue((String) document.getMetadata().get(RESOURCE_TYPE)))
                        .resourceId(UUID.fromString(document.getMetadata().get(RESOURCE_ID).toString()))
                        .accountMappingId(UUID.fromString(document.getMetadata().get(ACCOUNT_MAPPING_ID).toString()))
                        .build()
                ).toList();
    }
}
