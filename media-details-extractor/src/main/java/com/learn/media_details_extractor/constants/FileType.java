package com.learn.media_details_extractor.constants;

import lombok.Getter;

@Getter
public enum FileType {
    AUDIO("audio"),
    VIDEO("video"),
    IMAGE("image"),
    TEXT("text"),
    PDF("pdf");

    private final String type;

    FileType(String type) {
        this.type = type;
    }
}
