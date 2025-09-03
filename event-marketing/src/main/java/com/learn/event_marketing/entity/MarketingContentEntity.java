package com.learn.event_marketing.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "marketing_content")
public class MarketingContentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, insertable = false)
    private UUID id;

    @Column(name = "acct_mapping_id", nullable = false)
    private UUID accountMappingId;

    @Column(name = "event_id", nullable = false)
    private UUID eventId;

    @Column(name = "event_status", nullable = false)
    private String eventStatus;

    @Column(name = "event_title", nullable = false)
    private String eventTitle;

    @Lob
    @Column(name = "event_description", nullable = false, columnDefinition = "TEXT")
    private String eventDescription;

    @OneToMany(mappedBy = "marketingContent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChannelContentEntity> channelContent = new ArrayList<>();;

    @ElementCollection
    @CollectionTable(name = "seo_keywords", joinColumns = @JoinColumn(name = "marketing_content_id"))
    @MapKeyColumn(name = "keyword")
    @Column(name = "description", columnDefinition = "TEXT")
    private Map<String, String> seoKeywordsAndDescription = new HashMap<>();

    @Column(name = "created_at", updatable = false, insertable = false)
    private LocalDateTime createdAt;

    public void addChannelContent(ChannelContentEntity content) {
        channelContent.add(content);
        content.setMarketingContent(this);
    }

    public void removeChannelContent(ChannelContentEntity content) {
        channelContent.remove(content);
        content.setMarketingContent(null);
    }

}
