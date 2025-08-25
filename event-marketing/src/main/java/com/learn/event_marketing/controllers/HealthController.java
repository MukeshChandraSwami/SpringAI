package com.learn.event_marketing.controllers;

import com.learn.event_marketing.response.HealthResponse;
import com.learn.event_marketing.response.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.learn.event_marketing.constants.AppConstants.RoutingConstants.STATUS;


@RestController
@RequestMapping(STATUS)
public class HealthController {

    @GetMapping
    public Response checkHealth() {
        return HealthResponse.builder()
                .success(true)
                .responseCode(200)
                .status("Event Marketing Service is UP & Running")
                .build();
    }
}
