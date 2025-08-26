package com.learn.media_generator.repository;

import com.learn.media_generator.entities.MediaPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MediaRepository extends JpaRepository<MediaPost, UUID> {

    List<MediaPost> findByAccountMappingIdAndEventId(UUID accountMappingId, UUID eventId);
}
