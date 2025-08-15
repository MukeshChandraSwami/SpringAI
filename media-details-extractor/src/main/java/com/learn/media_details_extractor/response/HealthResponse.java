package com.learn.media_details_extractor.response;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class HealthResponse extends Response {

    private String status;
}
