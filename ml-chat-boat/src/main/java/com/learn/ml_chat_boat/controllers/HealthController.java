package com.learn.ml_chat_boat.controllers;

import com.learn.ml_chat_boat.response.HealthResponse;
import com.learn.ml_chat_boat.response.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.learn.ml_chat_boat.constants.AppConstants.RoutingConstants.STATUS;

@RestController
@RequestMapping(STATUS)
public class HealthController {

    @GetMapping
    public Response checkHealth() {
        return HealthResponse.builder()
                .success(true)
                .responseCode(200)
                .status("UP")
                .build();
    }
}
