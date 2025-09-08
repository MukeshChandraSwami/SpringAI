package com.learn.event_marketing.requests;

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
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class PersonalizedPostMediaRequest extends PersonalizedPostRequest {


    private List<PostDetails> postDetails;

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
    public enum ImageFormat {
        JPEG("jpeg"),
        PNG("png"),
        WEBP("webp");

        private final String format;

        ImageFormat(String format) {
            this.format = format;
        }
    }
}
