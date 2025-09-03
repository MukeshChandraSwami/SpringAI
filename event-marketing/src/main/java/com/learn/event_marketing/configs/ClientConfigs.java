package com.learn.event_marketing.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class ClientConfigs {

    @Bean(name = "media-generator-rest-template")
    public RestTemplate mediaGeneratorRestTemplate(@Value("${media.generator.url}") String url) {
        return getRestTemplate(url);
    }

    @Bean(name = "ml-rest-template")
    public RestTemplate mlRestTemplate(@Value("${ml.engine.url}") String url) {
        return getRestTemplate(url);
    }

    private RestTemplate getRestTemplate(String baseUrl) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(baseUrl));
        return restTemplate;
    }
}
