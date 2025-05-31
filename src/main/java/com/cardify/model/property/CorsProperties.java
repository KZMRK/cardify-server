package com.cardify.model.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "cors.allowed")
public record CorsProperties(String pathMapping, List<String> origins, List<String> headers, List<String> methods,
                             Long maxAge) {

}
