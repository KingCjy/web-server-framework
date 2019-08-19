package com.kingcjy.was.application.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingcjy.was.core.annotations.Bean;
import com.kingcjy.was.core.annotations.Configuration;

@Configuration
public class WebConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
