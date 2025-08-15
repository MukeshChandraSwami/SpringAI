package com.learn.media_details_extractor.requests;

import com.learn.media_details_extractor.constants.FileType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class MediaRequest {

    private String title;
    private FileType type;
    private String url;
}
