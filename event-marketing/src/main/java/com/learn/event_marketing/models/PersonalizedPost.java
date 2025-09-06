package com.learn.event_marketing.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonalizedPost {

    private List<Content> posts;

    @Getter
    @Setter
    @ToString
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Content extends ContentBase {
        private String title;
        private String description;
        private String imageSuggestion;
    }

    @Getter
    @Setter
    @ToString
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ContentBase {
        private UUID id;
        private List<String> imageUrls;
    }
}
