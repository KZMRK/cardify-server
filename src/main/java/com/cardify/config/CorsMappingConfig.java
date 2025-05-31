package com.cardify.config;

import com.cardify.model.property.CorsProperties;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsMappingConfig {

    @Bean
    public WebMvcConfigurer addCorsMappings(CorsProperties corsProperties) {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NotNull CorsRegistry registry) {
                registry
                        .addMapping(corsProperties.pathMapping())
                        .allowedOrigins(corsProperties.origins().toArray(String[]::new))
                        .allowedMethods(corsProperties.methods().toArray(String[]::new))
                        .maxAge(corsProperties.maxAge());
            }
        };
    }
}
