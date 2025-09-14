package com.learn.media_generator.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Data
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class PersonalizedPostRequest {

    private UUID eventId;
    private String title;
    private String description;
    private String eventDateAndTime;
    private Map<ThemeElement, String> themingDetails;
    private AttendeeProfile attendeeProfile;
    private SocialMediaChannel socialMediaChannel;
    private List<PostDetails> postDetails;

    @Getter
    @Setter
    @Builder
    public static class AttendeeProfile {
        private UUID attendeeId;
        private String firstName;
        private String lastName;
        private String photoUrl;
        private String designation;
        private String companyName;
        private AttendeeType attendeeType;
    }

    @Getter
    @Setter
    @Builder
    public static class PostDetails {
        private UUID id;
        private String title;
        private String description;
        private int height;
        private int width;
        private ImageFormat imageFormat;
    }

    @Getter
    public enum AttendeeType {
        SPEAKER("speaker"),
        ATTENDEE("attendee");

        private final String type;

        AttendeeType(String type) {
            this.type = type;
        }

        public static AttendeeType fromValue(String value) {
            for (AttendeeType type : AttendeeType.values()) {
                if (type.type.equalsIgnoreCase(value)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("No enum constant with value: " + value);
        }
    }

    @Getter
    public enum SocialMediaChannel {
        LINKEDIN("linkedin"),
        TWITTER("twitter"),
        FACEBOOK("facebook");

        private final String type;

        SocialMediaChannel(String type) {
            this.type = type;
        }

        public static SocialMediaChannel fromValue(String value) {
            for (SocialMediaChannel type : SocialMediaChannel.values()) {
                if (type.type.equalsIgnoreCase(value)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("No enum constant with value: " + value);
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
    public enum ThemeElement {
        PRIMARY_COLOR("primary_color"),
        SECONDARY_COLOR("secondary_color"),
        BACKGROUND_COLOR("background_color"),
        CARD_COLOR("card_color");

        private final String element;

        ThemeElement(String element) {
            this.element = element;
        }
    }
}
