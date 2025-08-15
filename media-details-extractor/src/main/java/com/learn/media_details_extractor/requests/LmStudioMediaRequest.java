package com.learn.media_details_extractor.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@EqualsAndHashCode
@Builder
public class LmStudioMediaRequest {

    private String model = "google/gemma-3-27b"; // default model
    private List<Message> messages;
    private float temperature;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Message {
        private String role;
        private String content;// "user", "system", "assistant"
        private List<Content> contents;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Content {
        private String type; // "text" or "image_url"
        private ImageUrl imageUrl; // if type is "image_url"
        private String text; // if type is "text"
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ImageUrl {
        private String url; // base64 string or file:// path
    }
}
