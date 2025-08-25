package com.learn.media_generator.controllers;

import com.learn.media_generator.requests.MediaRequest;
import com.learn.media_generator.response.Response;
import com.learn.media_generator.services.MediaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.learn.media_generator.constants.AppConstants.RoutingConstants.ACT;
import static com.learn.media_generator.constants.AppConstants.RoutingConstants.ACT_ID;


@RestController
@RequestMapping(ACT + ACT_ID)
public class MediaController {

    private final MediaService mediaService;

    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @PostMapping
    public Response generateMarketingContent(@RequestBody MediaRequest request) {

        return this.mediaService.generateMedia(request);
    }
}
