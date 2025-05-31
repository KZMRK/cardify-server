package com.cardify.config;

import com.cardify.model.property.CloudinaryProperties;
import com.cloudinary.Cloudinary;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@AllArgsConstructor
public class CloudinaryConfig {

    private final CloudinaryProperties cloudinaryProperties;

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(Map.of(
                "cloud_name", cloudinaryProperties.cloudName(),
                "api_key", cloudinaryProperties.apiKey(),
                "api_secret", cloudinaryProperties.apiSecret()
        ));
    }
}
