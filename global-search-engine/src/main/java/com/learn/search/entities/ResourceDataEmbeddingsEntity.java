package com.learn.search.entities;

import com.learn.search.constants.ResourceTypes;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
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
public class ResourceDataEmbeddingsEntity {

    @Id
    private UUID id;

    private UUID accountMappingId;

    private UUID resourceId;

    private ResourceTypes resourceType;

    private String content;

    private float[] embedding;

    private LocalDateTime createdAt;
}
