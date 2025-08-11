package com.learn.search.entities;

import com.learn.search.constants.ResourceTypes;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "resource_data_embeddings")
public class ResourceDataEmbeddingsEntity {

    @Id
    @Column(nullable = false, name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, name = "account_mapping_id")
    private UUID accountMappingId;

    @Column(nullable = false, name = "resource_id")
    private UUID resourceId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "resource_type")
    private ResourceTypes resourceType;

    @Column(nullable = false, columnDefinition = "TEXT", name = "content")
    private String content;

    @Column(columnDefinition = "vector(384)", name = "embedding")
    private float[] embedding;

    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "timestamp default now()", name = "created_at")
    private java.time.LocalDateTime createdAt;
}
