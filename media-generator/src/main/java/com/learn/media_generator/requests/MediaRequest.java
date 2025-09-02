package com.learn.media_generator.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Data
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class MediaRequest {

    private List<MediaDetails> mediaDetails;

    @Getter
    @Setter
    public static class MediaDetails {
        private UUID id;
        private UUID eventId;
        private String title;
        private String description;
        private String eventDateAndTime;
        private String industry; // Tech, Healthcare, Education, Finance, etc.
        private String audience; // Professionals, Students, Entrepreneurs, Doctors, etc.
        private MediaType mediaType; // Image, Video, Audio
        private EventType eventType; // Meeting, Webinar, Workshop, Conference
        private EventStatus eventStatus;
        private String registrationLink;
        private String demography;
        private String socialMediaPlatform; // Instagram, Facebook, LinkedIn, Twitter
        private String socialMediaPostTitle;
        private String socialMediaPostContent;
        private int width;
        private int height;
        private ImageFormat format; // JPEG, PNG, MP4, MP3, webp
    }

    @Getter
    public enum MediaType {
        IMAGE("image");

        private final String type;

        MediaType(String type) {
            this.type = type;
        }
    }

    @Getter
    public enum ImageFormat {
        JPEG("jpeg"),
        PNG("png"),
        WEBP("webp");

        private final String format;

        ImageFormat(String format) {
            this.format = format;
        }
    }

    @Getter
    public enum EventType {
        MEETING("meeting"),
        WEBINAR("webinar"),
        WORKSHOP("workshop"),
        CONFERENCE("conference");

        private final String type;

        EventType(String type) {
            this.type = type;
        }
    }

    @Getter
    public enum EventStatus {
        PENDING("pending"),
        ACTIVE("active"),
        COMPLETED("completed"),
        CLOSED("closed"),
        CANCELLED("cancelled");

        private final String type;

        EventStatus(String type) {
            this.type = type;
        }
    }
}
