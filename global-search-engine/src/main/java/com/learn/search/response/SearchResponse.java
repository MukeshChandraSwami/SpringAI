package com.learn.search.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class SearchResponse extends Response {

    private List<CreateSearchDataResponse> searchResponses;
}
