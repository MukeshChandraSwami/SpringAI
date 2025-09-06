package com.learn.event_marketing.controllers;

import com.learn.event_marketing.requests.PersonalizedPostRequest;
import com.learn.event_marketing.response.Response;
import com.learn.event_marketing.service.PersonalizedPostService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.learn.event_marketing.constants.AppConstants.RoutingConstants.ACT;
import static com.learn.event_marketing.constants.AppConstants.RoutingConstants.ACT_ID;
import static com.learn.event_marketing.constants.AppConstants.RoutingConstants.ATTENDE;
import static com.learn.event_marketing.constants.AppConstants.RoutingConstants.ATTENDE_ID;
import static com.learn.event_marketing.constants.AppConstants.RoutingConstants.EVENT;
import static com.learn.event_marketing.constants.AppConstants.RoutingConstants.EVENT_ID;
import static com.learn.event_marketing.constants.AppConstants.RoutingConstants.POST;

@RestController
@RequestMapping(ACT + ACT_ID + POST)
public class PersonalizedPostController {

    private PersonalizedPostService personalizedPostService;

    public PersonalizedPostController(PersonalizedPostService personalizedPostService) {
        this.personalizedPostService = personalizedPostService;
    }

    @PostMapping
    public Response generatePersonalizedPostContent(@PathVariable UUID acct_id,
                                             @RequestBody PersonalizedPostRequest request) {

        return this.personalizedPostService.generatePersonalizedPostContent(acct_id, request);
    }

    @GetMapping(EVENT + EVENT_ID + ATTENDE + ATTENDE_ID)
    public Response getPersonalizedPostContent(@PathVariable UUID acct_id, @PathVariable UUID event_id,
                                               @PathVariable UUID attendee_id) {
        return this.personalizedPostService.getAllPersonalizedPosts(acct_id, event_id, attendee_id);
    }
}
