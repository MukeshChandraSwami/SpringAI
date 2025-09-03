package com.learn.event_marketing.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MediaResponse {

    private boolean success;
    private String responseMsg;
    private int responseCode;
    private List<MediaDetailsResponse> mediaDetails;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class MediaDetailsResponse {
        private UUID id;
        private UUID resourceId;
        private String url;
        private String path;
    }
}
