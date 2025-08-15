package com.learn.media_details_extractor.response;


import com.learn.media_details_extractor.models.ImageAnalysis;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class MediaResponse extends Response {

    private ImageAnalysis imageAnalysis;
}
