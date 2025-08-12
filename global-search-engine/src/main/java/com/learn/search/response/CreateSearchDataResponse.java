package com.learn.search.response;

import com.learn.search.constants.ResourceTypes;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class CreateSearchDataResponse extends Response {

    private UUID id;
    private UUID resourceId;
    private String content;
    private ResourceTypes resourceType;
}
