package com.cardify.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UrlMappingConfig {

    @Getter
    private static final String[] PUBLIC_URLS = new String[]{
            "/api/v1/auth/*",
            "/"
    };

    public static String[] getPublicUrls() {
        return PUBLIC_URLS;
    }
}
