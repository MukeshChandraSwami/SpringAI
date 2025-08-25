package com.learn.media_generator.response;


import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class Response {

    private boolean success;
    private String responseMsg;
    private int responseCode;
}
