package com.learn.ml_chat_boat.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class TranslationsResponse extends Response {

    private Map<String, List<String>> translations;
}
