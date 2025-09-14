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
        LINKEDIN("linkedin",
                "conversational, approachable, and networking-oriented, capturing the excitement of being present. The mood is educational and inspirational, sharing key insights, takeaways, and energy from the sessions. Visuals are candid, lively, and authentic, showing genuine engagement and enthusiasm.",
                "professional, confident, and thought-leadership driven, highlighting expertise and authority. The mood is celebratory and grateful, showing pride in being part of the event while also inspiring peers. Visuals are polished, branded, and credible, signaling authority and trust."),
        TWITTER("twitter",
                "conversational, immediate, and engagement-focused—made for quick reactions, friendly and observant, sharing fast takeaways, surprising moments, or live impressions.",
                "bold, concise and authority-driven—designed to grab fast-scrolling attention, punchy headline or pull-quote, energetic and real-time, celebratory about the speaking role while signaling expertise. Also include a CTA to watch or follow live"),
        FACEBOOK("facebook",
                "Warm, excited and conversational — candid smile or action shot, natural/warm lighting. Community-first and humble — focus on connections, learning. soft warm palette, rounded fonts, subtle badge, short bold overlay text.",
                "Confident, polished and inspiring — professional headshot or staged speaking photo, crisp lighting. Value-led messaging — lead with the takeaway or topic rather than boilerplate. clean sans-serif typography, high contrast/brand colors, bold headline + ample negative space.");

        private final String type;
        private final String attendeeTone;
        private final String speakerTone;

        SocialMediaChannel(String type, String attendeeTone, String speakerTone) {
            this.type = type;
            this.attendeeTone = attendeeTone;
            this.speakerTone = speakerTone;
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
