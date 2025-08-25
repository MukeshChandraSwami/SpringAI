package com.learn.event_marketing.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class GenerateMarketingContentRequest {

    private String title;
    private String description;
    private String eventType; // Meeting, Webinar, Workshop, Conference
    private String audienceType; // Professionals, Students, Entrepreneurs, Doctors, etc.
    private String industryType; // Tech, Healthcare, Education, Finance, etc.
    private String demography;
    private long budget;
}
