package com.learn.event_marketing.repository;

import com.learn.event_marketing.entity.MarketingContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MarketingContentRepository extends JpaRepository<MarketingContentEntity, UUID> {

    MarketingContentEntity findByAccountMappingIdAndEventId(UUID accountMappingId, UUID eventId);
}
