package com.learn.media_details_extractor.services;

import com.learn.media_details_extractor.requests.MediaRequest;
import com.learn.media_details_extractor.response.Response;
import org.springframework.stereotype.Service;

@Service
public interface MediaService {

    public Response extractMediaDetails(MediaRequest request) throws Exception;
}
