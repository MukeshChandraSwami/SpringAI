package com.learn.event_marketing.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class MarketingContent {
    private String eventTitle;
    private String eventDescription;
    private Map<String, List<Content>> channelContent;
    private Map<String, String> seoKeywordsAndDescription;


    @Getter
    @Setter
    @ToString
    public static class Content {
        private String title;
        private String description;
        private String dateAndTimeToPost;
    }
}
