package com.cardify.service;

import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
public class PdfDocumentTextExtractorService implements DocumentTextExtractorProvider {

    @Override
    @SneakyThrows
    public String extract(MultipartFile file, int startPage, int endPage) {
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setStartPage(startPage);
            stripper.setEndPage(endPage);
            return stripper.getText(document);
        } catch (IOException e) {
            throw new RuntimeException("Не вдалося обробити PDF: " + e.getMessage(), e);
        }
    }
}
