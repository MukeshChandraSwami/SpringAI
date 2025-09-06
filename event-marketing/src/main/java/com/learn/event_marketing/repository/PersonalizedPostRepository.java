package com.learn.event_marketing.repository;

import com.learn.event_marketing.entity.MarketingContentEntity;
import com.learn.event_marketing.entity.PersonalizedPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PersonalizedPostRepository extends JpaRepository<PersonalizedPostEntity, UUID> {

    MarketingContentEntity findByAccountMappingIdAndEventId(UUID accountMappingId, UUID eventId);
}
