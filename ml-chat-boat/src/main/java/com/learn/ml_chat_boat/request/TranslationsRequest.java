package com.learn.ml_chat_boat.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TranslationsRequest {

    private String inputString;
    private List<String> langs;
}
