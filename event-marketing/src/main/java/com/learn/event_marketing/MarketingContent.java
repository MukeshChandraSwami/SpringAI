package com.learn.event_marketing;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "marketing_channels")
public class MarketingContent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, insertable = false)
    private UUID id;

    @Column(name = "acct_mapping_id", nullable = false)
    private UUID accountMappingId;

    @Column(name = "event_id", nullable = false)
    private UUID eventId;

    @Column(name = "channel_name", nullable = false)
    private List<String> channelName;

    @Column(name = "created_at", updatable = false, insertable = false)
    private LocalDateTime createdAt;
}
