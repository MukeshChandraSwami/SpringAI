package com.learn.event_marketing.models;

import com.learn.event_marketing.response.Response;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class PersonalizedPostResponse extends Response {

    private PersonalizedPost personalizedPost;
}
