package com.learn.event_marketing.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@Builder
@NoArgsConstructor
public class PersonalizedPostRequest {

    private UUID eventId;
    private String title;
    private String description;
    private String eventDateAndTime;
    private String themingDetails;
    private AttendeeProfile attendeeProfile;
    private SocialMediaChannel socialMediaChannel;

    /* ******
    * A person can attend sessions, can be a speaker of sessions.
    *
    * Following is the list of sessions the person is attending or speaking at.
    * */
    private List<Sessions> sessions;

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
    public static class Sessions {
        private String sessionTitle;
        private String sessionDescription;

        // Do not provide speaker details if the attendee is a speaker.
        private String speakerName;
        private String speakerDetails;
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
        INSTAGRAM("instagram"),
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
}
