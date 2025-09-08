package com.learn.event_marketing.response;

import com.learn.event_marketing.models.PersonalizedPost;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class PersonalizedPostResponse extends Response {

    private PersonalizedPost personalizedPost;
}
