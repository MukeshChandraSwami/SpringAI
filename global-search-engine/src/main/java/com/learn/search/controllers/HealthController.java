package com.learn.search.controllers;

import com.learn.search.response.HealthResponse;
import com.learn.search.response.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.learn.search.constants.AppConstants.RoutingConstants.STATUS;

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
