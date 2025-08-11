package com.learn.search.repositories;

import com.learn.search.constants.ResourceTypes;
import com.learn.search.entities.ResourceDataEmbeddingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface PgGlobalSearchRepository extends JpaRepository<ResourceDataEmbeddingsEntity, UUID> {

    @Query(value = """
        WITH query AS (
            SELECT CAST(:embedding AS vector(384)) AS embedding
        )
        SELECT rde.* FROM resource_data_embeddings rde, query q
        WHERE rde.account_mapping_id = :accountMappingId
        ORDER BY rde.embedding <=> q.embedding
        LIMIT :limit
    """, nativeQuery = true)
    List<ResourceDataEmbeddingsEntity> search(UUID accountMappingId, ResourceTypes resourceType, String embedding, int limit);
}
