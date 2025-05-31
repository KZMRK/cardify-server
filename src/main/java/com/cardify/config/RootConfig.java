package com.cardify.config;

import feign.jaxb.JAXBContextFactory;
import feign.jaxb.JAXBDecoder;
import feign.okhttp.OkHttpClient;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import java.nio.charset.StandardCharsets;

@Configuration
@ConfigurationPropertiesScan(basePackages = "com.cardify.model.property")
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class RootConfig {

    @Bean
    public JAXBDecoder jaxbDecoder() {
        JAXBContextFactory jaxbFactory = new JAXBContextFactory.Builder()
                .withMarshallerJAXBEncoding(StandardCharsets.UTF_8.name())
                .build();
        return new JAXBDecoder(jaxbFactory);
    }

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }
}
