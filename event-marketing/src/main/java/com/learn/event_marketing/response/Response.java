package com.learn.event_marketing.response;


import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class Response {

    private boolean success;
    private String responseMsg;
    private int responseCode;
}
