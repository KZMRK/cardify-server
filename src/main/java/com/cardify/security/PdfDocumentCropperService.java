package com.cardify.security;

import com.cardify.service.DocumentCropper;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

@Service
public class PdfDocumentCropperService implements DocumentCropper {

    @Override
    @SneakyThrows
    public OutputStream crop(MultipartFile file, int startPage, int endPage) {
        try (PDDocument source = PDDocument.load(file.getInputStream().readAllBytes());
             PDDocument target = new PDDocument()) {
            for (int i = startPage - 1; i < endPage && i < source.getNumberOfPages(); i++) {
                target.addPage(source.getPage(i));
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            target.save(outputStream);

            return outputStream;
        }
    }
}
