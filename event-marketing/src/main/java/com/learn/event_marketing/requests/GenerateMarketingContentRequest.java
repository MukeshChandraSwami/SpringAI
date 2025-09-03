package com.learn.event_marketing.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class GenerateMarketingContentRequest {

    private UUID eventId;
    private String title;
    private String description;
    private String eventDateAndTime;
    private EventType eventType; // Meeting, Webinar, Workshop, Conference
    private String audience; // Professionals, Students, Entrepreneurs, Doctors, etc.
    private String industryType; // Tech, Healthcare, Education, Finance, etc.
    private EventStatus eventStatus;
    private String demography;
    private long budget;

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

        public static EventStatus fromValue(String value) {
            for (EventStatus type : EventStatus.values()) {
                if (type.type.equalsIgnoreCase(value)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("No enum constant with value: " + value);
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

        public static EventType fromValue(String value) {
            for (EventType type : EventType.values()) {
                if (type.type.equalsIgnoreCase(value)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("No enum constant with value: " + value);
        }
    }
}
