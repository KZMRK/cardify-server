package com.cardify.model.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "security.jwt")
public record JwtProperties(String issuer, String signingKey, Long accessJwtExpTime,
                            Long refreshJwtExpTime) {
}
