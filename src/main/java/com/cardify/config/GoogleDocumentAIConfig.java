package com.cardify.config;

import com.cardify.model.property.GoogleDocumentAIProperties;
import com.google.cloud.documentai.v1.DocumentProcessorServiceClient;
import com.google.cloud.documentai.v1.DocumentProcessorServiceSettings;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class GoogleDocumentAIConfig {

    private final static String DOCUMENT_AI_ENDPOINT_TEMPLATE = "%s-documentai.googleapis.com:443";

    private final GoogleDocumentAIProperties documentAIProperties;

    @Bean
    @SneakyThrows
    public DocumentProcessorServiceClient documentProcessorServiceClient() {
        String endpoint = String.format(DOCUMENT_AI_ENDPOINT_TEMPLATE, documentAIProperties.location());
        DocumentProcessorServiceSettings settings = DocumentProcessorServiceSettings.newBuilder()
                .setEndpoint(endpoint)
                .build();
        return DocumentProcessorServiceClient.create(settings);
    }
}
