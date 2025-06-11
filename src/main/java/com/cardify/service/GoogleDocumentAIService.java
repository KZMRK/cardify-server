package com.cardify.service;

import com.cardify.model.exception.BadRequestException;
import com.cardify.model.property.GoogleDocumentAIProperties;
import com.cardify.model.type.ApiErrorStatusType;
import com.google.cloud.documentai.v1.Document;
import com.google.cloud.documentai.v1.DocumentProcessorServiceClient;
import com.google.cloud.documentai.v1.ProcessRequest;
import com.google.cloud.documentai.v1.ProcessResponse;
import com.google.cloud.documentai.v1.RawDocument;
import com.google.protobuf.ByteString;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class GoogleDocumentAIService implements OpticalCharacterRecognitionProvider {

    private final GoogleDocumentAIProperties documentAIProperties;
    private final DocumentProcessorServiceClient client;

    @Override
    @SneakyThrows
    public String extractText(MultipartFile file) {
        log.info("[extractText] invoked with fileName=[{}]", file.getOriginalFilename());
        ProcessRequest request = buildProcessRequest(file);
        ProcessResponse result = client.processDocument(request);
        Document documentResponse = result.getDocument();
        return Optional.of(documentResponse.getText())
                .filter(StringUtils::isNoneBlank)
                .orElseThrow(() -> new BadRequestException(ApiErrorStatusType.EMPTY_IMAGE, "Зображення не містить тексту"));
    }

    private ProcessRequest buildProcessRequest(MultipartFile file) {
        RawDocument document = buildRawDocument(file);
        String name = buildProcessorResourceName();
        return ProcessRequest.newBuilder()
                .setName(name)
                .setRawDocument(document)
                .build();
    }

    @SneakyThrows
    public RawDocument buildRawDocument(MultipartFile file) {
        String mimeType = ObjectUtils.defaultIfNull(file.getContentType(), MediaType.IMAGE_PNG_VALUE);
        return RawDocument.newBuilder()
                .setContent(ByteString.copyFrom(file.getBytes()))
                .setMimeType(mimeType)
                .build();
    }

    private String buildProcessorResourceName() {
        return String.format("projects/%s/locations/%s/processors/%s",
                documentAIProperties.projectId(),
                documentAIProperties.location(),
                documentAIProperties.processorId());
    }
}
