package com.kingcjy.was.core.configuration;

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
