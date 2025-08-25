package com.learn.search.response;


import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public abstract class Response {

    private boolean success;
    private String responseMsg;
    private int responseCode;
}
