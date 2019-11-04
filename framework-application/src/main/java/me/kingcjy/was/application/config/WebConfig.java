package me.kingcjy.was.application.config;

import me.kingcjy.was.core.annotations.Bean;
import me.kingcjy.was.core.annotations.Configuration;

@Configuration
public class WebConfig {
    @Bean
    public TestComponent testComponent() {
        TestComponent testComponent = new TestComponent(TestComponent.class.getName());
        return testComponent;
    }
}
