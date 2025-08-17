package com.learn.search.response;

import com.learn.search.models.SearchResult;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class SearchResponse extends Response {

    private List<SearchResult> searchResponses;
}
