package com.learn.media_details_extractor.services;

import com.learn.media_details_extractor.requests.MediaRequest;
import com.learn.media_details_extractor.response.Response;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface MediaService {

    default Response extractMediaDetails(MediaRequest request) throws Exception {
        return Response.builder()
                .success(false)
                .responseCode(501)
                .responseMsg("Media details extraction not implemented for this service")
                .build();
    }

    default Response extractMediaDetails(MultipartFile file) throws Exception {
        return Response.builder()
                .success(false)
                .responseCode(501)
                .responseMsg("Media details extraction not implemented for this service")
                .build();
    }
}
