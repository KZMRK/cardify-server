package com.cardify.model.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "google.document-ai")
public record GoogleDocumentAIProperties(String projectId, String location, String processorId) {

}
