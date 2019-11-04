package me.kingcjy.was.core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.kingcjy.was.core.annotations.Bean;
import me.kingcjy.was.core.annotations.Configuration;

@Configuration
public class ObjectMapperConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
