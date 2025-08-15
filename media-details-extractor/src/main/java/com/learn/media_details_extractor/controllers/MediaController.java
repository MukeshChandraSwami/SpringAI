package com.learn.media_details_extractor.controllers;

import com.learn.media_details_extractor.requests.MediaRequest;
import com.learn.media_details_extractor.response.Response;
import com.learn.media_details_extractor.services.MediaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.learn.media_details_extractor.constants.AppConstants.RoutingConstants.MEDIA;
import static com.learn.media_details_extractor.constants.AppConstants.RoutingConstants.VERSION;

@RestController
@RequestMapping(VERSION + MEDIA)
public class MediaController {

    private final MediaService mediaService;

    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @PostMapping
    public Response extractMediaDetails(@RequestBody MediaRequest request) throws Exception {

        return mediaService.extractMediaDetails(request);
    }
}
