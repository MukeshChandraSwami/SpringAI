package com.learn.media_generator.controllers;

import com.learn.media_generator.requests.PersonalizedPostRequest;
import com.learn.media_generator.response.Response;
import com.learn.media_generator.services.PersonalizedPostService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.learn.media_generator.constants.AppConstants.RoutingConstants.ACT;
import static com.learn.media_generator.constants.AppConstants.RoutingConstants.ACT_ID;
import static com.learn.media_generator.constants.AppConstants.RoutingConstants.ATTENDE;
import static com.learn.media_generator.constants.AppConstants.RoutingConstants.ATTENDE_ID;
import static com.learn.media_generator.constants.AppConstants.RoutingConstants.EVENT;
import static com.learn.media_generator.constants.AppConstants.RoutingConstants.EVENT_ID;
import static com.learn.media_generator.constants.AppConstants.RoutingConstants.POST;

@RestController
@RequestMapping(ACT + ACT_ID)
public class PersonalizedPostController {

    private final PersonalizedPostService personalizedPostService;

    public PersonalizedPostController(PersonalizedPostService personalizedPostService) {
        this.personalizedPostService = personalizedPostService;
    }

    @PostMapping(POST)
    public Response generatePersonalizedPost(@PathVariable UUID acct_id,
                                             @RequestBody PersonalizedPostRequest request) {
        return this.personalizedPostService.generatePersonalizedPost(acct_id, request);
    }

    @GetMapping(EVENT + EVENT_ID + ATTENDE + ATTENDE_ID + POST)
    public Response getAllPersonalizedPost(@PathVariable UUID acct_id, @PathVariable UUID event_id,
                                           @PathVariable UUID attendee_id) {
        return this.personalizedPostService.getAllPersonalizedPosts(acct_id, event_id, attendee_id);
    }
}
