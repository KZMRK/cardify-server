package com.cardify.config.api;

import com.cardify.config.api.decoder.YouTubeDecoder;
import feign.Feign;
import feign.Logger;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class ApiConfig {

    private final YouTubeDecoder youTubeDecoder;
    private final OkHttpClient okHttpClient;

    @Bean
    public YouTubeApi youTubeApi(@Value("${youtube.domain}") String domain) {
        return Feign.builder()
                .client(okHttpClient)
                .decoder(youTubeDecoder)
                .logger(new Slf4jLogger(YouTubeApi.class))
                .logLevel(Logger.Level.BASIC)
                .target(YouTubeApi.class, domain);
    }
}
