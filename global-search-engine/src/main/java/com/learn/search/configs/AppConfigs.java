package com.learn.search.configs;

import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfigs {

    @Bean
    public FilterExpressionBuilder filterExpressionBuilder() {
        return new FilterExpressionBuilder();
    }
}
