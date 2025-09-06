package com.learn.event_marketing.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "personalized_post_content")
public class PersonalizedPostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, insertable = false)
    private UUID id;

    @Column(name = "acct_mapping_id", nullable = false)
    private UUID accountMappingId;

    @Column(name = "event_id", nullable = false)
    private UUID eventId;

    @Column(name = "attendee_id", nullable = false)
    private UUID attendeeId;

    @Column(name = "event_title", nullable = false)
    private String eventTitle;

    @Column(name = "event_description", nullable = false, columnDefinition = "TEXT")
    private String eventDescription;

    @OneToMany(mappedBy = "personalizedPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PersonalizedPostChannelContentEntity> channelContent = new ArrayList<>();;

    @Column(name = "created_at", updatable = false, insertable = false)
    private LocalDateTime createdAt;

    public void addChannelContent(PersonalizedPostChannelContentEntity content) {
        channelContent.add(content);
        content.setPersonalizedPost(this);
    }

    public void removeChannelContent(PersonalizedPostChannelContentEntity content) {
        channelContent.remove(content);
        content.setPersonalizedPost(null);
    }
}
