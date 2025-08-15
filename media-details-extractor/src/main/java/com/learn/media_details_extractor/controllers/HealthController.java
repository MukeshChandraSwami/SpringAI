package com.learn.media_details_extractor.controllers;

import com.learn.media_details_extractor.response.HealthResponse;
import com.learn.media_details_extractor.response.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.learn.media_details_extractor.constants.AppConstants.RoutingConstants.STATUS;


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
