package com.cardify.service;

import com.cardify.model.exception.BadRequestException;
import com.cardify.model.type.ApiErrorStatusType;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;


@Service
public class PdfDocumentTextExtractorService implements DocumentTextExtractorProvider {

    @Override
    @SneakyThrows
    public String extract(MultipartFile file, int startPage, int endPage) {
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setStartPage(startPage);
            stripper.setEndPage(endPage);
            return Optional.of(stripper.getText(document))
                    .filter(StringUtils::isNotBlank)
                    .orElseThrow(() -> new BadRequestException(ApiErrorStatusType.EMPTY_DOCUMENT, "Документ не містить тексту"));
        } catch (IOException e) {
            throw new RuntimeException("Не вдалося обробити PDF: " + e.getMessage(), e);
        }
    }
}
