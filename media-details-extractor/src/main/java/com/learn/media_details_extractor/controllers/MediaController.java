package com.learn.media_details_extractor.controllers;

import com.learn.media_details_extractor.requests.MediaRequest;
import com.learn.media_details_extractor.response.Response;
import com.learn.media_details_extractor.services.MediaService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static com.learn.media_details_extractor.constants.AppConstants.RoutingConstants.MEDIA;
import static com.learn.media_details_extractor.constants.AppConstants.RoutingConstants.OPEN_AI;
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

    @PostMapping(value = OPEN_AI, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response extractMediaDetails(@RequestPart("file") MultipartFile file) throws Exception {
        return mediaService.extractMediaDetails(file);
    }
}
