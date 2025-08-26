package com.learn.media_generator.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class MediaResponse extends Response {

    private List<MediaDetailsResponse> mediaDetails;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class MediaDetailsResponse {
        private UUID id;
        private UUID resourceId;
        private String url;
        private String path;
    }
}
