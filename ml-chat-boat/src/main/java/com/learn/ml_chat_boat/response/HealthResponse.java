package com.learn.ml_chat_boat.response;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class HealthResponse extends Response {

    private String status;
}
