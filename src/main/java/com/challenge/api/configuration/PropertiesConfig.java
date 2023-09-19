package com.challenge.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@EnableAsync
@Configuration
public class PropertiesConfig {

    @Bean
    public Executor customExecutor() {
        return Executors.newFixedThreadPool(10);
    }

}
