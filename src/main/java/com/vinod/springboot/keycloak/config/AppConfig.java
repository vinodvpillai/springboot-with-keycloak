package com.vinod.springboot.keycloak.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class AppConfig {

    private Environment environment;

    public AppConfig(Environment environment) {
        this.environment=environment;
    }

    /**
     * Model mapper.
     *
     * @return the model mapper
     */
    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
