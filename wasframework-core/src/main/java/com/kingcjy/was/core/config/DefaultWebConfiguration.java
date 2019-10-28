package com.kingcjy.was.core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingcjy.was.core.annotations.Bean;
import com.kingcjy.was.core.annotations.Configuration;

@Configuration
public class DefaultWebConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}