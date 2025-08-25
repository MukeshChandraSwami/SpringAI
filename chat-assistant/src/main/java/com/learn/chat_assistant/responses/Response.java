package com.learn.chat_assistant.responses;


import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class Response {

    private boolean success;
    private String responseMsg;
    private int responseCode;
}
