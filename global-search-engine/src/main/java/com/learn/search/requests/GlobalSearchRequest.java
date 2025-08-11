package com.learn.search.requests;

import com.learn.search.constants.ResourceTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class GlobalSearchRequest {

    private String query;
    private ResourceTypes resourceType;
}
